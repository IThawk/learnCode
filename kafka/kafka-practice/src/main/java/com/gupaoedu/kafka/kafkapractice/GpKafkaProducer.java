package com.gupaoedu.kafka.kafkapractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/17-21:17
 */

@Component
public class GpKafkaProducer {

    @Autowired
    private KafkaTemplate<Integer,String> kafkaTemplate;

    public void send(){
        kafkaTemplate.send("test",1,"msgData");
    }

}
