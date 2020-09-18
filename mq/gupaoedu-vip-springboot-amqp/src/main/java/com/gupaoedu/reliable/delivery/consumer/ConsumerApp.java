package com.gupaoedu.reliable.delivery.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消息投递的可靠性保证
 */
@ComponentScan(basePackages = "com.gupaoedu.reliable.delivery.consumer")
public class ConsumerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ConsumerApp.class);
    }
}