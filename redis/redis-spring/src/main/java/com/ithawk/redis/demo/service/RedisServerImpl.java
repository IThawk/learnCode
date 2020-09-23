package com.ithawk.redis.demo.service;

import com.alibaba.fastjson.JSON;
import com.ithawk.redis.demo.User;
import com.ithawk.redis.demo.temple.RedisTemple;
import com.ithawk.redis.demo.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "redisServer")
public class RedisServerImpl implements RedisServer {



    @Override
    public User get(String key) {

        return new User();
    }
}
