package com.ithawk.demo.springboot.redis.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class SpringBootRedisCacheApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringBootRedisCacheApplication.class, args);

    }
}
