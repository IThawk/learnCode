package com.ithawk.redis.demo.config;

import com.ithawk.redis.demo.temple.RedisTemple;

public abstract class RedisCacheSelect<T> {

    private RedisTemple<T> redisTemple;

    public RedisCacheSelect(RedisTemple<T> redisTemple) {
        this.redisTemple = redisTemple;
    }

    public abstract T doGet(String key);

    public T select(String key, Class<T> tClass) {
        T t = (T)redisTemple.get(key, tClass);
        if (t == null) {
            t = doGet(key);
            redisTemple.setEx(key, t, 60);
        }
        return t;
    }
}
