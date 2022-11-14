package com.ithawk.demo.springboot.cache.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedissonController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/get")
    public String GetRedissonLock() throws InterruptedException {
        RLock rLock = redissonClient.getLock("myLock");
        rLock.lock();
        Thread.sleep(10000);
        rLock.unlock();
        return "OK";
    }
    @GetMapping("/lock")
    public String GetRedisson(){
        RLock rLock = redissonClient.getLock("myLock");
        rLock.lock();
        rLock.unlock();
        return "OK";
    }
}
