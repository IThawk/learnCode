package com.ithawk.demo.spring.kafka.kafkapractice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestKafkaConsumer {

    @KafkaListener(topics = {"test", "first_topic"})
    public void listener(ConsumerRecord record) {
        System.out.println("topic:" + record.topic() + "->offset:" + record.offset() + "->partition:" + record.partition());
        Optional msg = Optional.ofNullable(record.value());
        if (msg.isPresent()) {

            System.out.println("msg:" + msg.get());
        }
    }
}
