package com.ithawk.demo.redisson.semaphore;

import com.ithawk.demo.redisson.utils.JedisUtil;
import com.ithawk.demo.redisson.utils.RedissonClientUtils;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.CountDownLatch;

public class RedissonSemaphore {
    public static void main(String[] args) throws InterruptedException {
        RedissonClient redisson = RedissonClientUtils.getRedissonClient();
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil .set("mySemaphore","5");
        RSemaphore rSemaphore = redisson.getSemaphore("mySemaphore");

        for (int i=0;i<10;i++){
            Thread thread = new Thread(()->{
                try {
                    rSemaphore.acquire();
                    System.out.println("获取到信号");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        Thread.sleep(5000);
        System.out.println("开始释放到信号");
        for (int i=0;i<5;i++){
            Thread thread = new Thread(()->{
                try {
                    Thread.sleep(3000);
                    rSemaphore.release();
                    System.out.println("释放到信号");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        Thread.sleep(500000);
        redisson.shutdown();

    }
}
