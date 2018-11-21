package com.cucuyo.dto;

import lombok.Data;

@Data()
public class SearchDto {

    private String search;
    private int minimumPrice;
    private int maxPrice;
    private String page;

}
