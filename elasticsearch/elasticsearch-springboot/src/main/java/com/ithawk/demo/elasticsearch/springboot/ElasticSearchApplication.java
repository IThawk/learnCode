package com.ithawk.demo.elasticsearch.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(exclude =
        {
            DataSourceAutoConfiguration.class,
            MongoAutoConfiguration.class
        })

@EnableOpenApi //swagger
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }

}
