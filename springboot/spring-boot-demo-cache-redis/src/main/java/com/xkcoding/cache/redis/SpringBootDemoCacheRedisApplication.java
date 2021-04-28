package com.xkcoding.cache.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages="com.xkcoding.cache.redis")
//@ComponentScan(basePackages = {"com.xkcoding.cache.redis"})
public class SpringBootDemoCacheRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoCacheRedisApplication.class, args);
    }
}
