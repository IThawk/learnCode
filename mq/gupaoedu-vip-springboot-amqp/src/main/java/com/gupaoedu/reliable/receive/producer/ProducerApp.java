package com.gupaoedu.reliable.receive.producer;

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
 * 消息消费的可靠性保证——ACK
 */
@ComponentScan(basePackages = "com.gupaoedu.reliable.receive.producer")
public class ProducerApp{
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProducerApp.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitAdmin.declareExchange(new DirectExchange("GP_RELIABLE_RECEIVE_EXCHANGE", true, false, new HashMap<>()));

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("一条正常的消息".getBytes(), messageProperties);
        rabbitTemplate.send("GP_RELIABLE_RECEIVE_EXCHANGE", "gupao.test.reliable", message, new CorrelationData("201906180003"));

        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties2.setContentType("UTF-8");
        Message message2 = new Message("测试异常".getBytes(), messageProperties2);
        rabbitTemplate.send("GP_RELIABLE_RECEIVE_EXCHANGE", "gupao.test.reliable", message2, new CorrelationData("201906180004"));
    }
}