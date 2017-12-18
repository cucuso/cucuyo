package com.cucuyo.core.impl;

import com.cucuyo.core.AdPropertiesService;
import com.cucuyo.core.domain.AdProperties;
import com.cucuyo.core.domain.AdPropertiesRepository;
import com.cucuyo.core.domain.AdPropertiesSearch;
import com.cucuyo.core.domain.Page;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdPropertiesServiceImpl implements AdPropertiesService {

    private final AdPropertiesRepository propertiesRepository;

    @Override
    public Page<AdProperties> search(@NonNull AdPropertiesSearch searchRequest) {

        if (searchRequest.isAdvanced()) {
            return propertiesRepository.findAllByFullTextAndPriceBetween(searchRequest.getText(),
                    searchRequest.getFromPrice(),
                    searchRequest.getToPrice(),
                    searchRequest.getPageRequest());
        }

        return propertiesRepository.findAllByFullText(searchRequest.getText(), searchRequest.getPageRequest());
    }
}
