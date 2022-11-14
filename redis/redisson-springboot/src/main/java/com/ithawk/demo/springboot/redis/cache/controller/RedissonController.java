package com.ithawk.demo.springboot.redis.cache.controller;

import org.redisson.api.RBloomFilter;
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

    @GetMapping("/bloom")
    public String getBloom(){
        RBloomFilter<String> bloomFilter= redissonClient.getBloomFilter("myLock");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%,根据这两个参数会计算出底层的bit数组大小
        bloomFilter.tryInit(100000L,0.03);
        //将zhuge插入到布隆过滤器中
        bloomFilter.add("zhuge");
        bloomFilter.add("tuling");

        //判断下面号码是否在布隆过滤器中
        System.out.println(bloomFilter.contains("guojia"));//false
        System.out.println(bloomFilter.contains("baiqi"));//false
        System.out.println(bloomFilter.contains("zhuge"));//true
        return "OK";
    }
}
