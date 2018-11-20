package com.cucuyo.aws;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class OptionsHandler implements RequestHandler<Map<String, Object>, Object> {

  @Override
  public Object handleRequest(Map<String, Object> input, Context context) {
    log.error("handling lambda request for input <%s>", input);

    Map<String, String> optionsHeaders = new HashMap<>();

    if (input.get("httpMethod")
        .equals("OPTIONS")) {
      optionsHeaders.put("Access-Control-Allow-Headers", "content-type");
      optionsHeaders.put("Access-Control-Allow-Origin", "*");
      optionsHeaders.put("Access-Control-Allow-Methods", "OPTIONS, GET, HEAD, POST");

      log.info("the method is options");
      return new GatewayResponse("", optionsHeaders, 200);

    }

    return new GatewayResponse("Method is not OPTIONS", optionsHeaders, 500);
  }

}
