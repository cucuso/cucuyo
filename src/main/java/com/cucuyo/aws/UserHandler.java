package com.cucuyo.aws;

import static com.cucuyo.aws.HeaderConstants.BASIC;
import static com.cucuyo.aws.HeaderConstants.OPTIONS;

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

public class UserHandler implements RequestHandler<Map<String, ?>, Object> {

  public GatewayResponse handleRequest(final Map<String, ?> input, Context context) {

    String path = (String) input.get("path");
    String methodType = (String) input.get("httpMethod");

    switch (methodType.toLowerCase()) {
      case "options":
        return new GatewayResponse("{\"value\":\"success\"}", OPTIONS, 200);
      case "get":
        return getInfo(input);
      case "post":
        if (path.equals("/login")) {
          return login(input);
        }
        return createUser(input);
      default:
        return new GatewayResponse("{\"value\":\"http method not currently supported\"}", BASIC, 400);
    }

  }

  private GatewayResponse getInfo(final Map<String, ?> input) {
    String email = ((Map<String, String>) input.get("pathParameters")).get("email");

    UserService userService = new UserService();
    User userInfo = userService.getUserInfo(email);

    return new GatewayResponse(Util.buildString(userInfo), BASIC, 200);
  }

  private GatewayResponse createUser(final Map<String, ?> input) {
    UserService userService = new UserService();
    UserRequest body = extractBodyJson((String) input.get("body"));

    try {
    userService.createUser(body);
    }catch(IllegalArgumentException e) {
      return new GatewayResponse("{\"message\":\"user already exists\"}", BASIC, 400);
    }

    String token = buildToken(body.getEmail());

    return new GatewayResponse("{\"token\":\"" + token + "\"}", BASIC, 200);
  }

  private UserRequest extractBodyJson(String body) {

    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(body, UserRequest.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private GatewayResponse login(final Map<String, ?> input) {
    UserRequest body = extractBodyJson((String) input.get("body"));

    UserService userService = new UserService();
    User user = userService.getUserInfo(body.getEmail());

    if (user.getPassword()
        .equals(body.getPassword())) {

      String token = buildToken(body.getEmail());
      return new GatewayResponse("{\"token\":\"" + token + "\"}", BASIC, 200);

    } else {
      return new GatewayResponse("{\"response\":\"invalid password\"}", BASIC, 401);
    }
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
