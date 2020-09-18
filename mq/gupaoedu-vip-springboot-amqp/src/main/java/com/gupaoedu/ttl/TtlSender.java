package com.gupaoedu.ttl;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gupaoedu.ttl")
public class TtlSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TtlSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("4000"); // 消息的过期属性，单位ms
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message("这条消息4秒后过期".getBytes(), messageProperties);
        rabbitTemplate.send("GP_TTL_EXCHANGE", "gupao.ttl", message);

        // 随队列的过期属性过期，单位ms
        rabbitTemplate.convertAndSend("GP_TTL_EXCHANGE", "gupao.ttl", "这条消息");

    }
}
