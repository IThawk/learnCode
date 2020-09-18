package com.gupaoedu.amqp.template;

import com.gupaoedu.ttl.TtlSender;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: qingshan
 * @Date: 2019/8/14 16:44
 * @Description: 咕泡学院，只为更好的你
 */
@ComponentScan(basePackages = "com.gupaoedu.amqp.template")
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
