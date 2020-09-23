package com.ithawk.redis.demo;

import com.alibaba.fastjson.JSON;
import com.ithawk.redis.demo.config.SpringConfig;
import com.ithawk.redis.demo.service.RedisServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisSpringApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        applicationContext.start();
        RedisServer redisServer = applicationContext.getBean("redisServer", RedisServer.class);
        System.out.println(JSON.toJSONString(redisServer.get("key")));

        System.out.println(JSON.toJSONString(redisServer.getList("key")));

    }
}
