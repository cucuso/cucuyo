package com.cucuyo.data;

import com.cucuyo.core.domain.AdPropertiesRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.cucuyo.data")
public class DataAutoConfiguration {

    @Bean
    public AdPropertiesRepository propertiesRepository(DataAdPropertiesRepository repository, AdPropertiesEntityMapper mapper) {
        return new AdPropertiesRepositoryImpl(repository, mapper);
    }

    @Bean
    public AdPropertiesEntityMapper adPropertiesEntityMapper() {
        return Mappers.getMapper(AdPropertiesEntityMapper.class);
    }
}
