package com.cucuyo.core;

import com.cucuyo.core.domain.AdProperties;
import com.cucuyo.core.domain.AdPropertiesSearch;
import com.cucuyo.core.domain.Page;

public interface AdPropertiesService {

    Page<AdProperties> search(AdPropertiesSearch searchParams);
}
