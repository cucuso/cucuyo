package com.cucuyo.core.domain;

public interface AdPropertiesRepository {

    Page<AdProperties> findAllByFullText(String text, PageRequest pageRequest);

    Page<AdProperties> findAllByFullTextAndPriceBetween(String text, double from, double to, PageRequest pageRequest);
}
