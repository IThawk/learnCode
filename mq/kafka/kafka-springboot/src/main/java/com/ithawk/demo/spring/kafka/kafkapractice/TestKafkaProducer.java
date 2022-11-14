package com.ithawk.demo.spring.kafka.kafkapractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class TestKafkaProducer {

    @Autowired
    private KafkaTemplate<Integer,String> kafkaTemplate;

    /**
     * 发送消息
     */
    public void send(){
        kafkaTemplate.send("kafka-test",1,"msgData");
    }

}
