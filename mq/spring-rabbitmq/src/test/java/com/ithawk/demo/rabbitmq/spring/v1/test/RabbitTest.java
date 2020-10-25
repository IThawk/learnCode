package com.ithawk.demo.rabbitmq.spring.v1.test;

import com.ithawk.demo.rabbitmq.spring.v1.producer.MessageProducer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class RabbitTest {
    private ApplicationContext context = null;

    @Test
    public void sendMessage() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MessageProducer messageProducer = (MessageProducer) context.getBean("messageProducer");
        int k = 100;
        while (k > 0) {
            messageProducer.sendMessage("第" + k + "次发送的消息");
            k--;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
