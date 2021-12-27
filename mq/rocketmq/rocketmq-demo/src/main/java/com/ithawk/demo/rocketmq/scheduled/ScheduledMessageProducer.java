package com.ithawk.demo.rocketmq.scheduled;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;

/**
 * @className ScheduledMessageProducer
 * @description:  延迟队列消息
 * @author IThawk
 * @date 2021/12/27 10:05
 */
public class ScheduledMessageProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("192.168.56.101:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 2;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TopicTest", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            //注意这里的3指的不是3s，而是等级3 也就是延时10S
            //1到18分别对应messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(3);
            // Send the message

            System.out.println("-------------------------"+new Date() +"------------------------------------");
            producer.send(message);
            System.out.println("-------------------------------------------------------------");
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

}
