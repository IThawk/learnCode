package com.ithawk.redis.demo.service;

import com.ithawk.redis.demo.User;
import com.ithawk.redis.demo.aspect.RedisCache;
import com.ithawk.redis.demo.config.RedisCacheSelect;
import com.ithawk.redis.demo.temple.RedisTemple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "redisServer")
public class RedisServerImpl implements RedisServer {

    @Autowired
    RedisTemple redisTemple;

    @Override
    public User get(String key) {
        return new User("test", 12);
    }

    @Override
    public List<User> getList(String key) {
        List<User> list = new ArrayList<>();
        list.add(new User("test1", 12));
        list.add(new User("test2", 13));
        list.add(new User("test3", 14));
        list.add(new User("test4", 15));
        return list;
    }

    @Override
    public User getWithCache(String key) {
        return new RedisCacheSelect<User>(redisTemple) {

            @Override
            public User doGet(String key) {
                return new User("test", 12);
            }
        }.select(key, User.class);
    }
}
