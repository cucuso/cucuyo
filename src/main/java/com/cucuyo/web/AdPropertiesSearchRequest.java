package com.cucuyo.web;

import lombok.Data;

@Data
public class AdPropertiesSearchRequest {

    private String text;
    private double from;
    private double to;
    private String offset;
    private int limit;
}
