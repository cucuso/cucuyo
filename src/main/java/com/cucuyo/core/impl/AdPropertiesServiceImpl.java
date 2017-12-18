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
    public Page<AdProperties> search(@NonNull AdPropertiesSearch searchParams) {

        if (searchParams.isAdvanced()) {
            return propertiesRepository.findAllByFullTextAndPriceBetween(searchParams.getText(),
                    searchParams.getFromPrice(),
                    searchParams.getToPrice(),
                    searchParams.getPageRequest());
        }

        return propertiesRepository.findAllByFullText(searchParams.getText(), searchParams.getPageRequest());
    }
}
