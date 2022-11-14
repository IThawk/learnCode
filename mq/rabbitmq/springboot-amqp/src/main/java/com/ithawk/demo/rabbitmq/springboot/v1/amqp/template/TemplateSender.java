package com.ithawk.demo.rabbitmq.springboot.v1.amqp.template;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@ComponentScan(basePackages = "com.ithawk.demo.rabbitmq.springboot.v1.amqp.template")
public class TemplateSender {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TemplateSender.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("消息确认成功");
                } else {
                    // nack
                    System.out.println("消息确认失败");
                }
            }
        });

        rabbitTemplate.convertAndSend("GP_BASIC_FANOUT_EXCHANGE", "", "this is a msg");
    }

}
