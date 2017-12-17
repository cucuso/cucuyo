package com.cucuyo.core.domain;

import javax.validation.constraints.NotNull;

public interface AdPropertiesRepository {

    Page<AdProperties> findAllByFullText(@NotNull String text, PageRequest pageRequest);
}
