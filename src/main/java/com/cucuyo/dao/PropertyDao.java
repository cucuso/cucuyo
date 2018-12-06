package com.cucuyo.dao;

import java.util.ArrayList;
import java.util.List;

import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.model.Property;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Strings;

// TODO this class needs a lot of work
public class PropertyDao {

  // TODO get from environment variable
  private String cassandraContactPoint = "68.183.136.153";

  private static final String SELECT_ALL_QUERY = "SELECT * FROM cucuyo.properties";

  private static final String SEARCH_BASE_QUERY =
      "SELECT * FROM cucuyo.properties where expr(properties_index, '{filter:[ %s]}')";

  private static final String SELECT_SEARCH_DESCRIPTION_QUERY =
      "{type:\"wildcard\", field:\"description\", value:\"*%s*\"}";

  private static final String SELECT_FROM_TO_PRICE_QUERY =
      "{type:\"range\", field:\"price\", lower:\"%s\", upper:\"%s\"}";

  Session session = getCassandraSession();

  public PropertiesDto getProperties(SearchDto searchDto) {

    ResultSet results;

    String query = buildQuery(searchDto);

    if (searchDto.getPage() == null) {
      Statement stmt = new SimpleStatement(query);
      stmt.setFetchSize(50);
      results = session.execute(stmt);
    } else {
      PagingState pagingState = PagingState.fromString(searchDto.getPage());
      Statement stmt = new SimpleStatement(query);
      stmt.setFetchSize(50);
      stmt.setPagingState(pagingState);
      results = session.execute(stmt);
    }

    List<Property> properties = new ArrayList<>();
    Result<Property> ps = getMapper().map(results);

    int remaining = ps.getAvailableWithoutFetching();

    for (Property row : ps) {
      properties.add(row);
      if (--remaining == 0) {
        break;
      }
    }

    PagingState pagingState = results.getExecutionInfo()
        .getPagingState();
    String pagingStateStr = (pagingState != null) ? pagingState.toString() : null;
    return new PropertiesDto(properties, pagingStateStr);
  }

  public Property getProperty(String id) {
    return getMapper().get(id);
  }

  public Property saveProperty(Property property) {

    getMapper().save(property);
    return property;
  }

  private String escapeQuery(String in) {
    String regex = "([+\\-!\\(\\){}\\[\\]^\"~*?:\\\\]|[&\\|]{2})";
    return in.replaceAll(regex, "\\\\$1")
        .toLowerCase();
  }

  private String buildQuery(SearchDto searchDto) {

    String search = "";

    String searchQuery = searchDto.getSearch();
    searchQuery = (searchQuery != null) ? escapeQuery(searchQuery) : null;
    int from = searchDto.getMinimumPrice();
    int to = searchDto.getMaxPrice();

    if (Strings.isNullOrEmpty(searchQuery) && from == 0 && to == 0) {
      search = SELECT_ALL_QUERY;
    } else if (Strings.isNullOrEmpty(searchQuery) && (from != 0 || to != 0)) {
      search = String.format(SEARCH_BASE_QUERY, String.format(SELECT_FROM_TO_PRICE_QUERY, from, to));
    } else if (!Strings.isNullOrEmpty(searchQuery) && from == 0 && to == 0) {
      String wildcardAndRange = String.format(SELECT_SEARCH_DESCRIPTION_QUERY, searchQuery);
      search = String.format(SEARCH_BASE_QUERY, wildcardAndRange);
    } else {
      String wildcardAndRange = String.format(SELECT_SEARCH_DESCRIPTION_QUERY, searchQuery) + ","
          + String.format(SELECT_FROM_TO_PRICE_QUERY, from, to);
      search = String.format(SEARCH_BASE_QUERY, wildcardAndRange);
    }

    return search;
  }

  private Mapper<Property> getMapper() {
    MappingManager manager = new MappingManager(session);
    return manager.mapper(Property.class);
  }

  private Session getCassandraSession() {

    Cluster cluster = Cluster.builder()
        .addContactPoint(cassandraContactPoint)
        .build();

    return cluster.connect();
  }
}
