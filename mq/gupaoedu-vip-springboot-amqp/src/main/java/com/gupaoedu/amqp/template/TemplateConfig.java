package com.gupaoedu.amqp.template;

import com.gupaoedu.util.ResourceUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: qingshan
 * @Date: 2019/8/14 16:43
 * @Description: 咕泡学院，只为更好的你
 */
@Configuration
public class TemplateConfig {
    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey){
                System.out.println("回发的消息：");
                System.out.println("replyCode: "+replyCode);
                System.out.println("replyText: "+replyText);
                System.out.println("exchange: "+exchange);
                System.out.println("routingKey: "+routingKey);
            }
        });

        rabbitTemplate.setChannelTransacted(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
                    System.out.println("发送消息失败：" + cause);
                    throw new RuntimeException("发送异常：" + cause);
                }
            }
        });



        return rabbitTemplate;
    }
}