package com.ithawk.demo.mq.rocketmq.controller;

import com.ithawk.demo.mq.rocketmq.basic.SpringProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/MQTest")
@Api("MQTestController")
public class MQTestController {

    private final String topic = "TestTopic";

    @Resource
    private SpringProducer producer;

    @ApiOperation("发送MQ消息")
    @GetMapping("/sendMessage")
    public String sendMessage(String message){
        producer.sendMessage(topic,message);
        return "消息发送完成";
    }

    //这个发送事务消息的例子中有很多问题，需要注意下。
    @ApiOperation("发送MQ事务消息")
    @GetMapping("/sendTransactionMessage")
    public String sendTransactionMessage(String message) throws InterruptedException {
        producer.sendMessageInTransaction(topic,message);
        return "消息发送完成";
    }
}