package com.cucuyo.adapter;

import com.cucuyo.core.domain.AdPropertiesRepository;
import com.cucuyo.core.impl.AdPropertiesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdaptersConfiguration {

    @Bean
    public AdPropertiesServiceAdapter adPropertiesServiceAdapter(AdPropertiesRepository repository) {
        return new AdPropertiesServiceAdapter(new AdPropertiesServiceImpl(repository));
    }
}
