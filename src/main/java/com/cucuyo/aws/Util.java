package com.cucuyo.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
  
  private Util() {}

  public static String buildString(Object in) {

    ObjectMapper mapper = new ObjectMapper();
    String jsonString;

    try {
      jsonString = mapper.writeValueAsString(in);
    } catch (JsonProcessingException e) {
      jsonString = "unable to convert data to JSON";
    }

    return jsonString;
  }
}
