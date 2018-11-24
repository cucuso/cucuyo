package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC_HEADERS;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.model.User;
import com.cucuyo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class GetUserInfoHandler implements RequestHandler<Map<String, Object>, Object> {

  public GatewayResponse handleRequest(final Map<String, Object> input, Context context) {

    log.info("handling lambda request for input {}", input);
    
    log.info("path params {}", input.get("pathParameters"));

    String email = ((Map<String, String>) input.get("pathParameters")).get("email");

    log.info("email {}", email);
    UserService userService = new UserService();

    User userInfo = userService.getUserInfo(email);

    return new GatewayResponse(Util.buildString(userInfo), BASIC_HEADERS, 200);
  }

}
