package com.ithawk.redis.demo.service;

import com.ithawk.redis.demo.User;
import com.ithawk.redis.demo.aspect.RedisCache;

import java.util.List;

public interface RedisServer {

    @RedisCache(key = "test",clazz = User.class)
    User get(String key);

    @RedisCache(key = "list",clazz = User.class)
    List<User> getList(String key);

    User getWithCache(String key);
}
