package com.ithawk.redis.demo;

import com.alibaba.fastjson.JSON;
import com.ithawk.redis.demo.config.SpringConfig;
import com.ithawk.redis.demo.service.RedisServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RedisSpringApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        ((AnnotationConfigApplicationContext) applicationContext).start();
        RedisServer redisServer = applicationContext.getBean("redisServer", RedisServer.class);
        System.out.println(JSON.toJSONString(redisServer.get("test")));

    }
}
