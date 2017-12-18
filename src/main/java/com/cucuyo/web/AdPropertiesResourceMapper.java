package com.cucuyo.web;

import com.cucuyo.core.domain.AdProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface AdPropertiesResourceMapper {

    @Mappings({@Mapping(source = "id", target = "uuid")})
    AdPropertiesResource asAdPropertiesResource(AdProperties adProperties);
}
