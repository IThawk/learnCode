package com.ithawk.demo.spring.kafka.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SomeProducer")
@RestController
public class SomeProducer {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    // 从配置文件读取自定义属性
    @Value("${kafka.topic}")
    private String topic;


    @ApiOperation("发送kafka消息")
    @PostMapping("/msg/send")
    public String sendMsg(@RequestParam("message") String message) {
        template.send(topic, message);
        return "send success";
    }


    /**
     * @className SomeProducer
     * @description:  提交事务消息
     * @author IThawk
     * @date 2021/12/30 14:59
     */
    @ApiOperation("发送kafka事务消息")
    @PostMapping("/msg/sendTx")
    public String sendTrxMsg(@RequestParam("message") String message) {

        return template.executeInTransaction(new KafkaOperations.OperationsCallback<Integer, String, String>() {
            @Override
            public String doInOperations(KafkaOperations<Integer, String> operations) {
                for (int i = 0; i < 100; i++) {
                    //发到不同的主题的不同分区
                    template.send("hdfs_topic", i,message + "i");
                    template.send("es_topic", i,message + "i");
                    template.send("redis_topic",i, message + "i");
                }
                return "send ok";
            }
        });

    }
}
