package com.ithawk.demo.springboot.redis.cache.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * CacheManager->RedisCacheManager->RedisCache
 */
@EnableCaching()
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class RedisCacheConfig {

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        CacheProperties.Redis redis =cacheProperties.getRedis();
        if (redis.getTimeToLive()!=null){
            redisCacheConfiguration=redisCacheConfiguration.entryTtl(redis.getTimeToLive());
        }
        if (!redis.isCacheNullValues()){
            redisCacheConfiguration=redisCacheConfiguration.disableCachingNullValues();
        }
        if (redis.getKeyPrefix()!=null){
            redisCacheConfiguration=redisCacheConfiguration.prefixCacheNameWith(redis.getKeyPrefix());
        }
        return redisCacheConfiguration;
    }
}
