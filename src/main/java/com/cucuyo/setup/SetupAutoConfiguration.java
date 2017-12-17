package com.cucuyo.setup;

import com.cucuyo.data.DataAdPropertiesRepository;
import com.cucuyo.data.DataAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SetupConfigurationProperties.class)
@AutoConfigureAfter(DataAutoConfiguration.class)
public class SetupAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "cucuyo.setup",
            name = "import-enabled",
            matchIfMissing = true,
            havingValue = "true"
    )
    public CommandLineRunner dataImportCommandLineRunner(DataAdPropertiesRepository repository) {
        return new DataImportCommandLineRunner(repository);
    }
}
