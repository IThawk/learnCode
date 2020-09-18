package com.gupaoedu.amqp.container;

import com.gupaoedu.util.ResourceUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: qingshan
 * @Date: 2019/8/15 11:26
 * @Description: 咕泡学院，只为更好的你
 * 配置类的代码用不到，只用来演示
 *
 */
public class ContainerSender {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(new URI("amqp://guest:guest@localhost:5672"));
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        // 不用工厂模式也可以创建
        // SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setQueueNames("GP_BASIC_SECOND_QUEUE");
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("收到消息："+message);
            }
        });
        container.start();

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 1");
        template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 2");
        template.convertAndSend("GP_BASIC_SECOND_QUEUE", "msg 3");
    }

}
