package com.ithawk.demo.rocketmq.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 *  注册监听mq
 */
public class Consumer1 {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name2");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name3");
        // Specify name server addresses.
        consumer.setNamesrvAddr("192.168.56.101:9876");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);


//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
//        consumer.setMaxReconsumeTimes(18);

        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,

                                                            ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {


                    System.out.printf("%s 2Receive New Messages: %s %n",
                            Thread.currentThread().getName(), new String(msg.getBody()));


                }

                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
