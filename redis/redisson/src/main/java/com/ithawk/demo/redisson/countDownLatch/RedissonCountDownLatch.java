package com.ithawk.demo.redisson.countDownLatch;

import com.ithawk.demo.redisson.utils.JedisUtil;
import com.ithawk.demo.redisson.utils.RedissonClientUtils;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        RedissonClient redisson = RedissonClientUtils.getRedissonClient();
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil .set("myCountDownLatch","10");
        RCountDownLatch rCountDownLatch = redisson.getCountDownLatch("myCountDownLatch");

        for (int i=0;i<10;i++){
            Thread thread = new Thread(()->{
                try {
                    System.out.println("线程"+Thread.currentThread().getName()+"开始工作");
                    Thread.sleep(3000);
                    rCountDownLatch.countDown();
                    System.out.println("线程"+Thread.currentThread().getName()+"完成");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        System.out.println("等待完成");
        rCountDownLatch.await();
        System.out.println("完成了");
        redisson.shutdown();

    }
}
