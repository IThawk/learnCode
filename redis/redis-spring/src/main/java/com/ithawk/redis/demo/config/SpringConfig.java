package com.ithawk.redis.demo.config;

import com.ithawk.redis.demo.temple.RedisTemple;
import com.ithawk.redis.demo.temple.RedisTempleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.ithawk.redis")
//添加使用注解驱动Aop
@EnableAspectJAutoProxy
public class SpringConfig {


    @Bean
    public RedisTemple redisTemple() {
        return new RedisTempleImpl();
    }
}
