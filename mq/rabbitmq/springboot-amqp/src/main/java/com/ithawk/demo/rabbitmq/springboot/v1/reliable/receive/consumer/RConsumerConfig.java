package com.ithawk.demo.rabbitmq.springboot.v1.reliable.receive.consumer;


import com.ithawk.demo.rabbitmq.springboot.v1.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息消费的可靠性保证——ACK
 */
@Configuration
public class RConsumerConfig {
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

    @Bean("gpqueue")
    public Queue queue() {
        return new Queue("GP_RELIABLE_RECEIVE_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean("gpexchange")
    public DirectExchange exchange() {
        return new DirectExchange("GP_RELIABLE_RECEIVE_EXCHANGE", true, false, new HashMap<>());
    }

    @Bean
    public Binding binding(@Qualifier("gpqueue") Queue queue, @Qualifier("gpexchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("gupao.test.reliable");
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames("GP_RELIABLE_RECEIVE_QUEUE");
        messageListenerContainer.setConcurrentConsumers(5);
        messageListenerContainer.setMaxConcurrentConsumers(10);

        Map<String, Object> argumentMap = new HashMap();
        messageListenerContainer.setConsumerArguments(argumentMap);

        messageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return "A_PARTICULAR_STRING";
            }
        });

        // 设置消息确认模式为手动模式
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        messageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    System.out.println(new String(message.getBody(), "UTF-8"));
                    System.out.println(message.getMessageProperties());
                    if ("测试异常".equals(new String(message.getBody(), "UTF-8"))) {
                        throw new RuntimeException();
                    } else {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    }
                } catch (Exception e) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            }
        });

        return messageListenerContainer;
    }
}