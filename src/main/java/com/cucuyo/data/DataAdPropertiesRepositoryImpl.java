package com.cucuyo.data;

import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.SimpleStatement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.ArrayList;

import static com.cucuyo.data.AdPropertiesEntity.getTableName;


@RequiredArgsConstructor
public class DataAdPropertiesRepositoryImpl implements PagingDataAdPropertiesRepository {

    private static final String SEARCH_BASE_QUERY = "SELECT * FROM %s.%s where expr(%s, '{filter:[%s]}') LIMIT 1;";

    private static final String SEARCH_INDEX = "ad_properties_search_index";

    private static final String SEARCH_FILTER = "{type:\"wildcard\", field:\"description\", value:\"*%s*\"}";
    private final CassandraTemplate cassandraTemplate;
    private final AdPropertiesEntityMapper mapper;
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Override
    public Page<AdPropertiesEntity> findAllByFullText(@NonNull String text, @NonNull PageRequest pageRequest) {
        val filter = String.format(SEARCH_FILTER, text);
        val args = new Object[]{keyspace, getTableName(), SEARCH_INDEX, filter};
        val query = String.format(SEARCH_BASE_QUERY, args);
        return execute(query, pageRequest);
    }

    private Page<AdPropertiesEntity> execute(String query, PageRequest pageRequest) {
        return cassandraTemplate.query(session -> {
            val statement = new SimpleStatement(query);
            statement.setFetchSize(pageRequest.getLimit());
            pageRequest.getOffset().ifPresent(offset -> statement.setPagingState(PagingState.fromString(offset)));
            return session.prepare(statement);
        }, resultSet -> {
            val content = new ArrayList<AdPropertiesEntity>();
            resultSet.forEach(row -> content.add(mapper.asAdPropertiesEntity(row)));
            val pageState = resultSet.getExecutionInfo().getPagingState();
            val pageStateString = pageState != null ? pageState.toString() : null;
            return new Page<>(0, pageStateString, content);
        });
    }

    @Override
    public Page<AdPropertiesEntity> findAllByFullTextAndPriceBetween(@NonNull String text, @NonNull double from, @NonNull double to, @NonNull PageRequest pageRequest) {

        return null;
    }
}
