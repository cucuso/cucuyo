package com.cucuyo.data;

import com.cucuyo.core.domain.AdProperties;
import com.cucuyo.core.domain.AdPropertiesRepository;
import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class AdPropertiesRepositoryImpl implements AdPropertiesRepository {

    private final DataAdPropertiesRepository adPropertiesRepository;

    private final AdPropertiesEntityMapper entityMapper;

    @Override
    public Page<AdProperties> findAllByFullText(@NotNull String text, @NotNull PageRequest pageRequest) {
        val page = adPropertiesRepository.findAllByFullText(text, pageRequest);
        return page.map(entityMapper::asAdProperties);
    }

    @Override
    public Page<AdProperties> findAllByFullTextAndPriceBetween(String text, double from, double to, PageRequest pageRequest) {
        val page = adPropertiesRepository.findAllByFullTextAndPriceBetween(text, from, to, pageRequest);
        return page.map(entityMapper::asAdProperties);
    }
}
