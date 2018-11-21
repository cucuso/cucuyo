package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.OPTIONS_HEADERS;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class OptionsHandler implements RequestHandler<Map<String, Object>, Object> {

  @Override
  public Object handleRequest(Map<String, Object> input, Context context) {

    log.info("handling lambda request for input <%s>", input);

    return new GatewayResponse("", OPTIONS_HEADERS, 200);

  }

}
