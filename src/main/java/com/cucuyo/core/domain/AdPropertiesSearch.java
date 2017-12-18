package com.cucuyo.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AdPropertiesSearch {

    private String text;
    private double fromPrice;
    private double toPrice;
    private PageRequest pageRequest;

    public boolean isAdvanced() {
        return fromPrice < toPrice;
    }
}
