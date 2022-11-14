package com.ithawk.demo.springboot.redis.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheConfiguration;


/**
 *
 *  org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
 *  org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 *  org.springframework.data.redis.cache.RedisCacheConfiguration
 *  CacheAutoConfiguration->导入 RedisAutoConfiguration
 *                        @Bean
 * 	                    RedisCacheManager cacheManager
 *
 */
@SpringBootApplication
public class SpringBootRedisCacheApplication {
    public static void main(String[] args) {


        SpringApplication.run(SpringBootRedisCacheApplication.class, args);

    }
}
