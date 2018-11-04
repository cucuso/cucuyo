package com.cucuyo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class Config {

  @Value("${cassandra.contact.point}")
  private String cassandraContactPoint;

  @Bean
  public Session setupCassandraConfig() {

    Cluster cluster = Cluster.builder()
        .addContactPoint(cassandraContactPoint)
        .build();

    return cluster.connect();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedOrigins("*")
            .allowedHeaders("*");
      }
    };
  }

}
