package com.ithawk.demo.mongo.springboot.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Value("${mongodb.url}")
    private String mongoUrl;

    @Value("${mongodb.db}")
    private String mongoDb;

    public @Bean
    MongoClient mongoClient() {
//        return MongoClients.create("mongodb://127.0.0.1:27017");

        return MongoClients.create(mongoUrl);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "productdb");

        return new MongoTemplate(mongoClient(), mongoDb);
    }
}