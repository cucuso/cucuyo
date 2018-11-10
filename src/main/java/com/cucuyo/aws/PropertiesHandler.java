package com.cucuyo.aws;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.service.PropertyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// TODO figure out issue with logger
@Slf4j()
public class PropertiesHandler implements RequestHandler<Object, Object> {

  // TODO handle all operations and call controller from here based on type
  public GatewayResponse handleRequest(final Object input, Context context) {

    log.info("handling lambda request for input <%s>", input);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");

    ObjectMapper mapper = new ObjectMapper();
    PropertiesDto dto = new PropertyService().getProperties(new SearchDto(), null);
    String jsonInString;

    try {
      jsonInString = mapper.writeValueAsString(dto);
    } catch (JsonProcessingException e) {
      jsonInString = "unable to convert data to JSON";
    }

    return new GatewayResponse(jsonInString, headers, 200);
  }

}
