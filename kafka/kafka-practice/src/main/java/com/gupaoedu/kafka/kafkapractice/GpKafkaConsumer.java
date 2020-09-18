package com.gupaoedu.kafka.kafkapractice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/17-21:18
 */
@Component
public class GpKafkaConsumer {

    @KafkaListener(topics = {"test","first_topic"})
    public void listener(ConsumerRecord record){
        Optional msg=Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
        }
    }
}
