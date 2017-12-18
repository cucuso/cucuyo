package com.cucuyo.web;

import com.cucuyo.core.domain.AdPropertiesSearch;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
@DecoratedWith(AdPropertiesSearchRequestMapperDecorator.class)
public interface AdPropertiesSearchRequestMapper {

    @Mappings({
            @Mapping(source = "from", target = "fromPrice"),
            @Mapping(source = "to", target = "toPrice")
    })
    AdPropertiesSearch asAdPropertiesSearch(AdPropertiesSearchRequest request);
}
