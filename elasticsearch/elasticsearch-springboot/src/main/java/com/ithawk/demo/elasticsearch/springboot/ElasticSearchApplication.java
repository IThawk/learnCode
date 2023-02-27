package com.ithawk.demo.elasticsearch.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.Date;

@SpringBootApplication(exclude =
        {
            DataSourceAutoConfiguration.class,
            MongoAutoConfiguration.class
        })

@EnableOpenApi //swagger
@Slf4j
public class ElasticSearchApplication {

    public static void main(String[] args) {
        log.info("logback ELK成功接入了，时间：" + new Date());
        SpringApplication.run(ElasticSearchApplication.class, args);
    }

}
