package com.cucuyo.web;

import com.cucuyo.core.domain.AdPropertiesSearch;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(AdPropertiesSearchRequestMapperDecorator.class)
public interface AdPropertiesSearchRequestMapper {

    AdPropertiesSearch asAdPropertiesSearch(AdPropertiesSearchRequest request);
}
