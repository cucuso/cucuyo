package com.cucuyo.core.domain;

import lombok.Data;

@Data
public final class AdPropertiesSearch {

    private String title;
    private double fromPrice;
    private double toPrice;
    private PageRequest pageRequest;
}
