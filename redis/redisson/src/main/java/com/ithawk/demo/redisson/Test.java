package com.ithawk.demo.redisson;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import reactor.core.publisher.Mono;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer()
                //可以用"rediss://"来启用SSL连接
                .setAddress("redis://192.168.56.101:6379");
        RedissonClient redisson = Redisson.create(config);
        RLock lock = redisson.getLock("myLock");
        System.out.println("获取到锁");
        lock.lock();
        Thread.sleep(50000);
        lock.unlock();
        System.out.println("释放锁");
        redisson.shutdown();

    }
}
