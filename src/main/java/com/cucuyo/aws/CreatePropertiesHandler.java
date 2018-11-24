package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC_HEADERS;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.CreateRequest;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class CreatePropertiesHandler implements RequestHandler<Map<String, ?>, Object> {

  public GatewayResponse handleRequest(final Map<String, ?> input, Context context) {

    log.info("handling lambda request for input {}", input);

    PropertyService propertyService = new PropertyService();
    //
    CreateRequest body = extractBodyJson((String) input.get("body"));

    log.info("{}", body);

    String address = body.getAddress();
    String description = body.getDescription();
    String phone = body.getPhone();
    Double price = body.getPrice();
    Float latitude = body.getLatitude();
    Float longitude = body.getLongitude();
    List<String> images = body.getImages();

    propertyService.saveProperty(address, description, phone, Double.valueOf(price), latitude, longitude, images);

    return new GatewayResponse("{\"value\":\"success\"}", BASIC_HEADERS, 200);
  }

  private CreateRequest extractBodyJson(String body) {

    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(body, CreateRequest.class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}
