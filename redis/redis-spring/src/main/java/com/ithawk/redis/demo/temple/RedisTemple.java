package com.ithawk.redis.demo.temple;

public interface RedisTemple<T> {

     Object get(String key, Class<T> clazz);

    void set(String key, T t);

    void setEx(String key, Object t, int seconds);
}
