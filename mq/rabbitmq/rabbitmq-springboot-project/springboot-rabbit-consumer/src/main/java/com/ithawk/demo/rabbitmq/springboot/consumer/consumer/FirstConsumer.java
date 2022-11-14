package com.ithawk.demo.rabbitmq.springboot.consumer.consumer;


import com.ithawk.demo.rabbitmq.springboot.consumer.entity.Merchant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@PropertySource("classpath:mq.properties")
@RabbitListener(queues = "${com.ithawk.firstqueue}", containerFactory="rabbitListenerContainerFactory")
public class FirstConsumer {

    @RabbitHandler
    public void process(@Payload Merchant merchant){
        System.out.println("First Queue received msg : " + merchant.getName());
    }

}
