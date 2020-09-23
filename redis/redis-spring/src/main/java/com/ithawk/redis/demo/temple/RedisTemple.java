package com.ithawk.redis.demo.temple;

public interface RedisTemple<T> {

     T get(String key);
     void set(String key,T t);
}
