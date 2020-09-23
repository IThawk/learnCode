package com.ithawk.redis.demo.service;

import com.ithawk.redis.demo.User;

public interface RedisServer {

    User get(String key);
}
