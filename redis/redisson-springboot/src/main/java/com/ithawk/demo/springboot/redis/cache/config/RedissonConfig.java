//package com.ithawk.demo.springboot.redis.cache.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.cache.CacheProperties;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.SpringProperties;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * CacheManager->RedisCacheManager->RedisCache
// */
//@EnableConfigurationProperties(RedisProperties.class) //开启属性配置绑定功能
//@Configuration
//public class RedissonConfig {
//    @Bean
//    public RedissonClient getRedissonClient(RedisProperties redisProperties) {
//        String host = redisProperties.getHost();
//        int port = redisProperties.getPort();
//        Config config = new Config();
//        String url = "redis://" + host + ":" + port;
//        config.useSingleServer()
//                //可以用"rediss://"来启用SSL连接
//                .setAddress(url);
//        RedissonClient redisson = Redisson.create(config);
//        return redisson;
//    }
//}
