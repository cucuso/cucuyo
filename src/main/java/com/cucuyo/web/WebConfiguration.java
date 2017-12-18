package com.cucuyo.web;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public AdPropertiesSearchRequestMapper adPropertiesSearchRequestMapper() {
        return Mappers.getMapper(AdPropertiesSearchRequestMapper.class);
    }

    @Bean
    public AdPropertiesResourceMapper adPropertiesResourceMapper() {
        return Mappers.getMapper(AdPropertiesResourceMapper.class);
    }
}
