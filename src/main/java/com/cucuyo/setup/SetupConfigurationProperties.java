package com.cucuyo.setup;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("cucuyo.setup")
public class SetupConfigurationProperties {

    private boolean importEnabled;
}
