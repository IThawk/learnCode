package com.ithawk.demo.spring.kafka.kafkapractice;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class TestKafkaConsumer {

    @KafkaListener(topics = {"test", "first_topic"})
    public void listener(ConsumerRecord record) {
        log.info("topic:" + record.topic() + "->offset:" + record.offset() + "->partition:" + record.partition());
        Optional msg = Optional.ofNullable(record.value());
        if (msg.isPresent()) {

            log.info("msg:" + msg.get());
        }
    }
}
