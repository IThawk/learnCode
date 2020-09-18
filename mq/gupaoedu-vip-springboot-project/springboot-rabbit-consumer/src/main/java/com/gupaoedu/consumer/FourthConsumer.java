package com.gupaoedu.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: qingshan
 * @Date: 2018/10/20 17:04
 * @Description: 咕泡学院，只为更好的你
 */
@Component
@PropertySource("classpath:gupaomq.properties")
@RabbitListener(queues = "${com.gupaoedu.fourthqueue}", containerFactory="rabbitListenerContainerFactory")
public class FourthConsumer {
    @RabbitHandler
    public void process(String message) throws IOException {

        System.out.println("Fourth Queue received msg : " + message);
    }
}
