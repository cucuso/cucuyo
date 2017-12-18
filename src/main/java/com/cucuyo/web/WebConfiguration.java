package com.cucuyo.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfiguration {

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    private static ObjectMapper initMapper(ObjectMapper mapper) {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @PostConstruct
    public void init() {
        if (objectMapper != null) {
            initMapper(objectMapper);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return initMapper(new ObjectMapper());
    }

    @Bean
    public AdPropertiesSearchRequestMapper adPropertiesSearchRequestMapper() {
        return Mappers.getMapper(AdPropertiesSearchRequestMapper.class);
    }

    @Bean
    public AdPropertiesResourceMapper adPropertiesResourceMapper() {
        return Mappers.getMapper(AdPropertiesResourceMapper.class);
    }
}
