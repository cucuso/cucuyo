package com.cucuyo.data;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.cucuyo.data")
public class DataAutoConfiguration {

    @Bean
    public AdPropertiesEntityMapper adPropertiesEntityMapper() {
        return Mappers.getMapper(AdPropertiesEntityMapper.class);
    }
}
