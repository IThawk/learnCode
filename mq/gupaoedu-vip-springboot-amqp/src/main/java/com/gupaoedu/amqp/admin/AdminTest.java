package com.gupaoedu.amqp.admin;

import com.gupaoedu.reliable.receive.producer.ProducerApp;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gupaoedu.amqp")
public class AdminTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdminTest.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);

        // 声明一个交换机
        rabbitAdmin.declareExchange(new DirectExchange("GP_ADMIN_EXCHANGE", false, false));

        // 声明一个队列
        rabbitAdmin.declareQueue(new Queue("GP_ADMIN_QUEUE", false, false, false));

        // 声明一个绑定
        rabbitAdmin.declareBinding( new Binding("GP_ADMIN_QUEUE", Binding.DestinationType.QUEUE,
                "GP_ADMIN_EXCHANGE", "admin", null));

    }
}
