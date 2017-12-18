package com.cucuyo.adapter;

import com.cucuyo.core.AdPropertiesService;
import com.cucuyo.core.domain.AdProperties;
import com.cucuyo.core.domain.AdPropertiesSearch;
import com.cucuyo.core.domain.Page;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdPropertiesServiceAdapter {

    private final AdPropertiesService propertiesService;

    public Page<AdProperties> search(AdPropertiesSearch searchParams) {
        return propertiesService.search(searchParams);
    }
}
