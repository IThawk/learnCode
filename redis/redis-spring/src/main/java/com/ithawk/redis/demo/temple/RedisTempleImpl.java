package com.ithawk.redis.demo.temple;

import com.alibaba.fastjson.JSON;
import com.ithawk.redis.demo.User;
import com.ithawk.redis.demo.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;


public class RedisTempleImpl implements RedisTemple {

    @Autowired
    JedisUtil jedisUtil;

    @Override
    public Object get(String key) {
        return JSON.parse(jedisUtil.get(key));
    }

    @Override
    public void set(String key, Object o) {
        jedisUtil.set(key, JSON.toJSONString(o));
    }
}
