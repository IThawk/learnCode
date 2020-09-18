package com.gupaoedu.basic.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    /**
     * 都可以使用缺省对象
     * @return
     * @throws Exception
     */
    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri("amqp://guest:guest@localhost:5672");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
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

    // 两个交换机
    @Bean("topicExchange")
    public TopicExchange getTopicExchange(){
        return new TopicExchange("GP_BASIC_TOPIC_EXCHANGE");
    }

    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange(){
        return  new FanoutExchange("GP_BASIC_FANOUT_EXCHANGE");
    }

    // 三个队列
    @Bean("firstQueue")
    public Queue getFirstQueue(){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl",6000);
        Queue queue = new Queue("GP_BASIC_FIRST_QUEUE", false, false, true, args);
        return queue;
    }

    @Bean("secondQueue")
    public Queue getSecondQueue(){
        return new Queue("GP_BASIC_SECOND_QUEUE");
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue(){
        return new Queue("GP_BASIC_THIRD_QUEUE");
    }

    // 两个绑定
    @Bean
    public Binding bindSecond(@Qualifier("secondQueue") Queue queue, @Qualifier("topicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("#.gupao.#");
    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }



}
