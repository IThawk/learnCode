package com.ithawk.redis.demo.config;

import com.ithawk.redis.demo.temple.RedisTemple;
import com.ithawk.redis.demo.temple.RedisTempleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ithawk.redis")
public class SpringConfig {


    @Bean
    public RedisTemple gpRpcServer() {
        return new RedisTempleImpl();
    }
}
