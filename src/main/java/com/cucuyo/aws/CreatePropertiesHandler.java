package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC_HEADERS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class CreatePropertiesHandler implements RequestHandler<Map<String, Object>, Object> {

  public GatewayResponse handleRequest(final Map<String, Object> input, Context context) {

    log.info("handling lambda request for input {}", input);

    PropertyService propertyService = new PropertyService();

    Map<String, ?> body = extractBodyJson((String) input.get("body"));

    if (body != null) {
      String address = (String) body.get("address");
      String description = (String) body.get("description");
      Integer price = (Integer) body.get("price");
      List<String> images = (ArrayList<String>)body.get("images");

      propertyService.saveProperty(address, description, Double.valueOf(price), images);
    }

    return new GatewayResponse("{\"value\":\"success\"}", BASIC_HEADERS, 200);
  }

  private Map<String, ?> extractBodyJson(String body) {

    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(body, HashMap.class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  private String[] extractImgArr(String arr) {
    log.info("img array "+ arr);
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(arr, String[].class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}

/*
 * 
 * body={ "address": "2345 entre 23 y 14", "description": "tremendo gao", "price": 1000, "images": [
 * 
 */
