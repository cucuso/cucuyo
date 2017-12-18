package com.cucuyo.data;

import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.google.common.base.Strings;
import com.stratio.cassandra.lucene.builder.Builder;
import com.stratio.cassandra.lucene.builder.search.condition.BooleanCondition;
import com.stratio.cassandra.lucene.builder.search.condition.Condition;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.cucuyo.data.AdPropertiesEntity.getTableName;


@RequiredArgsConstructor
class DataAdPropertiesRepositoryImpl implements PagingDataAdPropertiesRepository {

    private static final String SEARCH_BASE_QUERY = "SELECT * FROM %s WHERE expr(ad_properties_search_index, '%s');";
    private static final String SEARCH_COUNT_BASE_QUERY = "SELECT COUNT(*) FROM %s WHERE expr(ad_properties_search_index, '%s');";
    private final CassandraTemplate cassandraTemplate;
    private final AdPropertiesEntityMapper mapper;



    @Override
    public Page<AdPropertiesEntity> findAllByFullText(@NonNull String text, @NonNull PageRequest pageRequest) {
        val descCondition = Builder.wildcard("description", appendWildcards(text));
        val filters = Builder.search().filter(descCondition).build();
        val query = String.format(SEARCH_BASE_QUERY, getTableName(), filters);
        val totalElementsQuery = String.format(SEARCH_COUNT_BASE_QUERY, getTableName(), filters);
        return execute(query, totalElementsQuery, pageRequest);
    }

    private static String appendWildcards(String text) {
        return "*" + text + "*";
    }

    private Page<AdPropertiesEntity> execute(String query, String totalElementsQuery, PageRequest pageRequest) {
        val statement = new SimpleStatement(query);
        statement.setFetchSize(pageRequest.getLimit());
        pageRequest.getOffset().ifPresent(offset -> statement.setPagingState(PagingState.fromString(offset)));
        val resultSet = cassandraTemplate.getSession().execute(statement);
        val content = fetchRows(resultSet, pageRequest.getLimit());
        val pageState = resultSet.getExecutionInfo().getPagingState();
        val pageStateString = pageState != null ? pageState.toString() : null;
        val totalElements = cassandraTemplate.query(totalElementsQuery).one().getLong(0);
        return new Page<>(totalElements, pageStateString, content);
    }

    private List<AdPropertiesEntity> fetchRows(ResultSet rows, int fetchSize) {
        val content = new ArrayList<AdPropertiesEntity>(fetchSize);
        int remaining = rows.getAvailableWithoutFetching();
        val iterator = rows.iterator();

        while (iterator.hasNext() && remaining > 0) {
            content.add(mapper.asAdPropertiesEntity(iterator.next()));
            remaining--;
        }

        return content;
    }

    @Override
    public Page<AdPropertiesEntity> findAllByFullTextAndPriceBetween(String text, @NonNull double from, @NonNull double to, @NonNull PageRequest pageRequest) {
        val condition = getAdvancedFilterCondition(text, from, to);
        val filters = Builder.search().filter(condition).build();
        val query = String.format(SEARCH_BASE_QUERY, getTableName(), filters);
        val totalElementsQuery = String.format(SEARCH_COUNT_BASE_QUERY, getTableName(), filters);
        return execute(query, totalElementsQuery, pageRequest);
    }

    private static BooleanCondition getAdvancedFilterCondition(String text, @NonNull double from, @NonNull double to) {
        val conditions = new Condition[2];
        conditions[0] = Builder
                .range("price")
                .lower(from)
                .includeLower(true)
                .upper(to)
                .includeUpper(true);

        if (!Strings.isNullOrEmpty(text)) {
            conditions[1] = Builder.wildcard("description", appendWildcards(text));
        }

        return Builder.bool().must(conditions);
    }
}
