package com.ithawk.demo.elasticsearch.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication(exclude =
        {
            DataSourceAutoConfiguration.class,
            MongoAutoConfiguration.class
        })
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer sourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer sourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        sourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return sourcesPlaceholderConfigurer;
    }
}
