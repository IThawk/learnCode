package com.gupaoedu.amqp.admin;

import com.gupaoedu.util.ResourceUtil;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        // admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            public String createConsumerTag(String queue) {
                return null;
            }
        });
        return container;
    }

}
