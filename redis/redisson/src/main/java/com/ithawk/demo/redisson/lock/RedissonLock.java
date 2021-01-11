package com.ithawk.demo.redisson.lock;

import com.ithawk.demo.redisson.utils.RedissonClientUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.Semaphore;

public class RedissonLock {
    public static void main(String[] args) throws InterruptedException {
        RedissonClient redisson = RedissonClientUtils.getRedissonClient();
        RLock lock = redisson.getLock("myLock");
        System.out.println("获取到锁");
        lock.lock();
        Thread.sleep(50000);
        lock.unlock();
        System.out.println("释放锁");
        redisson.shutdown();

    }


}
