package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC;
import static com.cucuyo.aws.HeaderConstants.OPTIONS;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.CreateRequest;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertiesHandler implements RequestHandler<Map<String, Object>, Object> {

  public GatewayResponse handleRequest(final Map<String, Object> input, Context context) {

    String methodType = (String) input.get("httpMethod");
    System.out.println("gott in"+ methodType);

    switch (methodType.toLowerCase()) {
      case "options":
        return new GatewayResponse("{\"value\":\"success\"}", OPTIONS, 200);
      case "get":
        return getProperties(input);
      case "post":
        return createProperty(input);
      default:
        return new GatewayResponse("{\"value\":\"http method not currently supported\"}", BASIC, 400);
    }

  }

  private GatewayResponse getProperties(final Map<String, Object> input) {
    Map<String, String> queryParams = (Map<String, String>) input.get("queryStringParameters");
    SearchDto searchDto = new SearchDto();

    if (queryParams != null) {
      searchDto = buildSearchDto(queryParams);
    }
    PropertiesDto properties = new PropertyService().getProperties(searchDto);
    return new GatewayResponse(Util.buildString(properties), BASIC, 200);
  }

  private GatewayResponse createProperty(final Map<String, Object> input) {
    CreateRequest body = extractBodyJson((String) input.get("body"));

    String address = body.getAddress();
    String description = body.getDescription();
    String phone = body.getPhone();
    Double price = body.getPrice();
    Float latitude = body.getLatitude();
    Float longitude = body.getLongitude();
    List<String> images = body.getImages();

    new PropertyService().saveProperty(address, description, phone, Double.valueOf(price), latitude, longitude, images);

    return new GatewayResponse("{\"value\":\"success\"}", BASIC, 200);
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

  private CreateRequest extractBodyJson(String body) {

    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(body, CreateRequest.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
