package com.ithawk.redis.demo.temple;

import com.alibaba.fastjson.JSON;
import com.ithawk.redis.demo.util.JedisUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;


public class RedisTempleImpl<T> implements RedisTemple<T> {

    @Autowired
    JedisUtil jedisUtil;

    @Override
    public Object get(String key, Class<T> clazz) {
        String value = jedisUtil.get(key);
        if (value == null) {
            return null;
        }
        if (value.startsWith("[")) {
            return JSON.parseObject(value, List.class);
        } else {
            try {
                return JSON.parseObject(jedisUtil.get(key), clazz);
            } catch (Exception e) {
                return JSON.parseObject(jedisUtil.get(key), HashMap.class);
            }


        }


    }

    @Override
    public void set(String key, T t) {
        jedisUtil.set(key, JSON.toJSONString(t));
    }

    @Override
    public void setEx(String key, Object t, int seconds) {
        jedisUtil.setex(key, seconds, JSON.toJSONString(t));
    }
}
