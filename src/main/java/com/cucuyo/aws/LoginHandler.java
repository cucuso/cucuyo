package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC_HEADERS;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.cucuyo.dto.GatewayResponse;
import com.cucuyo.dto.UserRequest;
import com.cucuyo.model.User;
import com.cucuyo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class LoginHandler implements RequestHandler<Map<String, ?>, Object> {

  public GatewayResponse handleRequest(final Map<String, ?> input, Context context) {

    log.info("handling lambda request for input {}", input);
    UserRequest body = extractBodyJson((String) input.get("body"));

    UserService userService = new UserService();
    User user = userService.getUserInfo(body.getEmail());

    if (user.getPassword()
        .equals(body.getPassword())) {

      String token = buildToken(body.getEmail());
      return new GatewayResponse("{\"token\":\"" + token + "\"}", BASIC_HEADERS, 200);

    } else {
      return new GatewayResponse("{\"response\":\"invalid password\"}", BASIC_HEADERS, 401);
    }

  }

  private UserRequest extractBodyJson(String body) {

    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(body, UserRequest.class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  private String buildToken(String email) {
    String token = "";
    try {
      Algorithm algorithm = Algorithm.HMAC256("MySecretIsBetterThanYours");
      token = JWT.create()
          .withIssuer("cucuyo")
          .withExpiresAt(new Date(2237390170000L))
          .withClaim("email", email)
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
    return token;
  }
}
