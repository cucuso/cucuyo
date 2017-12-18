package com.cucuyo.data;

import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.stratio.cassandra.lucene.builder.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.cucuyo.data.AdPropertiesEntity.getTableName;


@RequiredArgsConstructor
class DataAdPropertiesRepositoryImpl implements PagingDataAdPropertiesRepository {

    private static final String SEARCH_BASE_QUERY = "SELECT * FROM %s.%s WHERE expr(%s, '%s');";
    private static final String SEARCH_COUNT_BASE_QUERY = "SELECT COUNT(*) FROM %s.%s WHERE expr(%s, '%s');";
    private static final String SEARCH_INDEX = "ad_properties_search_index";
    private static final String SEARCH_ADV_INDEX = "ad_properties_adv_search_index";

    private static final String FULLTEXT_FILTER = "{type:\"wildcard\", field:\"description\", value:\"*%s*\"}";
    private static final String PRICE_FILTER = "{type:\"range\", field:\"price\", lower:\"%s\", upper:\"%s\"}";

    private final CassandraTemplate cassandraTemplate;
    private final AdPropertiesEntityMapper mapper;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    private static String escapeText(String text) {
        String regex = "([+\\-!\\(\\){}\\[\\]^\"~*?:\\\\]|[&\\|]{2})";
        return text.replaceAll(regex, "\\\\$1").toLowerCase();
    }

    @Override
    public Page<AdPropertiesEntity> findAllByFullText(@NonNull String text, @NonNull PageRequest pageRequest) {
        val descCondition = Builder.wildcard("description", text);
        val filters = Builder.search().filter(descCondition).build();
        val args = new Object[]{keyspace, getTableName(), SEARCH_INDEX, filters};
        val query = String.format(SEARCH_BASE_QUERY, args);
        return execute(query, pageRequest);
    }

    private Page<AdPropertiesEntity> execute(String query, PageRequest pageRequest) {
        val statement = new SimpleStatement(query);
        statement.setFetchSize(pageRequest.getLimit());
        pageRequest.getOffset().ifPresent(offset -> statement.setPagingState(PagingState.fromString(offset)));
        val resultSet = cassandraTemplate.getSession().execute(statement);
        val content = fetchRows(resultSet, pageRequest.getLimit());
        val pageState = resultSet.getExecutionInfo().getPagingState();
        val pageStateString = pageState != null ? pageState.toString() : null;
        return new Page<>(0, pageStateString, content);
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
    public Page<AdPropertiesEntity> findAllByFullTextAndPriceBetween(@NonNull String text, @NonNull double from, @NonNull double to, @NonNull PageRequest pageRequest) {
        val fullTextFilter = String.format(FULLTEXT_FILTER, escapeText(text));
        val priceFilter = String.format(PRICE_FILTER, from, to);
        val args = new Object[]{keyspace, getTableName(), SEARCH_ADV_INDEX, fullTextFilter + "," + priceFilter};
        val query = String.format(SEARCH_BASE_QUERY, args);
        return execute(query, pageRequest);
    }
}
