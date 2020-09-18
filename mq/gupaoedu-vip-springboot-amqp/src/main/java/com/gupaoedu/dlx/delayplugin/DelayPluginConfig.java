package com.gupaoedu.dlx.delayplugin;

import com.gupaoedu.util.ResourceUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayPluginConfig {
    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri("amqp://guest:guest@192.168.8.133:5672");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("delayExchange")
    public TopicExchange exchange() {
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-delayed-type", "direct");
        return new TopicExchange("GP_DELAY_EXCHANGE", true, false, argss);
    }

    @Bean("delayQueue")
    public Queue deadLetterQueue() {
        return new Queue("GP_DELAY_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean
    public Binding bindingDead(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#"); // 无条件路由
    }

}
