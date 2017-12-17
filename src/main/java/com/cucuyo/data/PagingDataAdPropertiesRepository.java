package com.cucuyo.data;

import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;

interface PagingDataAdPropertiesRepository {

    Page<AdPropertiesEntity> findAllByFullText(String text, PageRequest pageRequest);

    Page<AdPropertiesEntity> findAllByFullTextAndPriceBetween(String text, double from, double to, PageRequest pageRequest);
}
