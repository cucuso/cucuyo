package com.cucuyo.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.cucuyo.data")
public class DataAutoConfiguration {
}
