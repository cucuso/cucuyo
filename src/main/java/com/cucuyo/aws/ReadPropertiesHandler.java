package com.cucuyo.aws;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class ReadPropertiesHandler implements RequestHandler<Map<String, Object>, Object> {

  public GatewayResponse handleRequest(final Map<String, Object> input, Context context) {

    log.info("handling lambda request for input <%s>", input);

    // GETTING DATA FROM DB

    // ObjectMapper mapper = new ObjectMapper();
    // PropertiesDto dto = new PropertyService().getProperties(new SearchDto(), null);
    // String jsonInString;

    // try {
    // jsonInString = mapper.writeValueAsString(dto);
    // } catch (JsonProcessingException e) {
    // jsonInString = "unable to convert data to JSON";
    // }

    Map<String, String> headers = new HashMap<>();
    headers.put("content-type", "application/json");
    headers.put("X-Custom-Header", "application/json");
    headers.put("Access-Control-Allow-Origin", "*");

    return new GatewayResponse("{\"value\":\"success\"}", headers, 200);
  }

}
