package com.gupaoedu.reliable.delivery.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.util.HashMap;

/**
 * 消息投递的可靠性保证
 */
@ComponentScan(basePackages = "com.gupaoedu.reliable.delivery.producer")
public class ProducerApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProducerApp.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitAdmin.declareExchange(new DirectExchange("GP_RELIABLE_SEND_EXCHANGE", true, false, new HashMap<>()));

        MessageProperties messageProperties = new MessageProperties();
        // 消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("各部门注意，今天8点发车".getBytes(), messageProperties);

        rabbitTemplate.send("GP_RELIABLE_SEND_EXCHANGE", "gupao.tech", message, new CorrelationData("201906180001"));
        rabbitTemplate.send("GP_RELIABLE_SEND_EXCHANGE", "gupao.tech.wrong", message, new CorrelationData("201906180002"));
    }
}
