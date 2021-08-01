package com.ithawk.demo.springboot.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


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
@EnableCaching  //开启缓存启动类的注解从启动类移到这里
public class SpringBootRedisCacheApplication {
    public static void main(String[] args) {


        SpringApplication.run(SpringBootRedisCacheApplication.class, args);

    }
}
