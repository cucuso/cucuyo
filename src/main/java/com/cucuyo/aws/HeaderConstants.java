package com.cucuyo.aws;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class HeaderConstants {

  private HeaderConstants() {}

  public static final Map<String, String> BASIC_HEADERS = ImmutableMap.of("content-type", "application/json",
      "X-Custom-Header", "application/json", "Access-Control-Allow-Origin", "*");
  
  public static final Map<String, String> OPTIONS_HEADERS = ImmutableMap.of("Access-Control-Allow-Headers", "content-type, Authorization",
      "Access-Control-Allow-Methods", "OPTIONS, GET, HEAD, POST", "Access-Control-Allow-Origin", "*");

}
