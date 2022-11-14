package com.ithawk.demo.rabbitmq.springboot.v1.reliable.receive.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消息消费的可靠性保证——ACK
 * 存在消息重复消费的问题
 *
 */
@ComponentScan(basePackages = "com.ithawk.demo.rabbitmq.springboot.v1.reliable.receive.consumer")
public class ConsumerApp{
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ConsumerApp.class);
    }
}
