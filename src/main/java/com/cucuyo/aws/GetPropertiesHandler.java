package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC_HEADERS;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.service.PropertyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class GetPropertiesHandler implements RequestHandler<Map<String, Object>, Object> {

  public GatewayResponse handleRequest(final Map<String, Object> input, Context context) {

    log.info("handling lambda request for input {}", input);

    Map<String, String> queryParams = (Map<String, String>) input.get("queryStringParameters");
    SearchDto searchDto = new SearchDto();

    if (queryParams != null) {
      searchDto = buildSearchDto(queryParams);
    }

    PropertiesDto properties = getProperties(searchDto);

    return new GatewayResponse(Util.buildString(properties), BASIC_HEADERS, 200);
  }

  private PropertiesDto getProperties(SearchDto searchDto) {
    return new PropertyService().getProperties(searchDto);
  }

  private SearchDto buildSearchDto(Map<String, String> in) {
    SearchDto searchDto = new SearchDto();
    searchDto.setSearch(in.get("search"));
    if (in.get("max") != null)
      searchDto.setMaxPrice(Integer.parseInt(in.get("max")));
    if (in.get("minimum") != null)
      searchDto.setMinimumPrice(Integer.parseInt(in.get("minimum")));
    searchDto.setPage(in.get("page"));

    return searchDto;
  }

}
