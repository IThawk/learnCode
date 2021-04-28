package com.xkcoding.mq.kafka.controller;

import com.xkcoding.mq.kafka.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ithawk
 */
@RestController
public class SendMessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    // private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, Message> kafkaTemplate;

     @GetMapping("/send1/{message}")
     public void send(@PathVariable String message) {
         this.kafkaTemplate.send("test",  new Message("ithawk", message));
         ListenableFuture<SendResult<String, Message>> future = this.kafkaTemplate.send("test",  new Message("ithawk", message));
         future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
             @Override
             public void onSuccess(SendResult<String, Message> result) {
                 logger.info("成功发送消息：{}，offset=[{}]", message, result.getRecordMetadata().offset());
             }

             @Override
             public void onFailure(Throwable ex) {
                 logger.error("消息：{} 发送失败，原因：{}", message, ex.getMessage());
             }
         });
     }

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        this.kafkaTemplate.send("test", new Message("ithawk", message));
    }
}
