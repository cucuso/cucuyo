package com.cucuyo.dao;

public class UserDao {

  private static final String SELECT_ALL_QUERY = "SELECT * FROM cucuyo.props";

  private static final String SEARCH_BASE_QUERY =
      "SELECT * FROM cucuyo.props where expr(props_index, '{filter:[ %s]}')";

  private static final String SELECT_SEARCH_DESCRIPTION_QUERY =
      "{type:\"wildcard\", field:\"description\", value:\"*%s*\"}";

  private static final String SELECT_FROM_TO_PRICE_QUERY =
      "{type:\"range\", field:\"price\", lower:\"%s\", upper:\"%s\"}";

//  Session session;
//
//  public User createUser(User user) {
//    getMapper().save(user);
//    return user;
//  }
//
//  private String escapeQuery(String in) {
//    String regex = "([+\\-!\\(\\){}\\[\\]^\"~*?:\\\\]|[&\\|]{2})";
//    return in.replaceAll(regex, "\\\\$1")
//        .toLowerCase();
//  }
//
//  private String buildQuery(SearchDto searchDto) {
//
//    String search = "";
//
//    String searchQuery = searchDto.getSearch();
//    searchQuery = (searchQuery != null) ? escapeQuery(searchQuery) : null;
//    int from = searchDto.getMinimumPrice();
//    int to = searchDto.getMaxPrice();
//
//    if (Strings.isNullOrEmpty(searchQuery) && from == 0 && to == 0) {
//      search = SELECT_ALL_QUERY;
//    } else if (Strings.isNullOrEmpty(searchQuery) && (from != 0 || to != 0)) {
//      search = String.format(SEARCH_BASE_QUERY, String.format(SELECT_FROM_TO_PRICE_QUERY, from, to));
//    } else if (!Strings.isNullOrEmpty(searchQuery) && from == 0 && to == 0) {
//      String wildcardAndRange = String.format(SELECT_SEARCH_DESCRIPTION_QUERY, searchQuery);
//      search = String.format(SEARCH_BASE_QUERY, wildcardAndRange);
//    } else {
//      String wildcardAndRange = String.format(SELECT_SEARCH_DESCRIPTION_QUERY, searchQuery) + ","
//          + String.format(SELECT_FROM_TO_PRICE_QUERY, from, to);
//      search = String.format(SEARCH_BASE_QUERY, wildcardAndRange);
//    }
//
//    return search;
//  }
//
//  private Mapper<User> getMapper() {
//    MappingManager manager = new MappingManager(session);
//    Mapper<User> mapper = manager.mapper(User.class);
//    return mapper;
//  }
}
