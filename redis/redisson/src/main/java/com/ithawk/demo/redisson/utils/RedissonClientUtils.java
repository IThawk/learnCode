package com.ithawk.demo.redisson.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonClientUtils {
    public static RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useSingleServer()
                //可以用"rediss://"来启用SSL连接
                .setAddress("redis://192.168.56.101:6379");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
