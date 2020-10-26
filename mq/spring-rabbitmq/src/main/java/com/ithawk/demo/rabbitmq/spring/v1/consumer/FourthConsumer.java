package com.ithawk.demo.rabbitmq.spring.v1.consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


public class FourthConsumer implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(FourthConsumer.class);

    public void onMessage(Message message) {
        logger.info("The fourth consumer received message : " + message);
    }
}
