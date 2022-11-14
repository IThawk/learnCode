package com.ithawk.demo.rocketmq.example;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 *  rocketMQ
 */
public class RocketMqConsumer {
    public static void main(String[] args) throws MQClientException {
        //设置消费组
        DefaultMQPushConsumer consumer=
                new DefaultMQPushConsumer("producer_group");
        consumer.setNamesrvAddr("192.168.56.101:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest1","*");

        //设置集群消费
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("Receive Message: "+list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //签收
            }
        });

        //设置顺序消费
//        consumer.registerMessageListener(new MessageListenerOrderly() {
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
//
//                MessageExt  messageExt=list.get(0);
//                //TODO  --
//                // Throw Exceptio
//                // 重新发送该消息
//                // DLQ（通用设计）
//                if(messageExt.getReconsumeTimes()==3){  //消息重发了三次
//                    //持久化 消息记录表
//                    return ConsumeOrderlyStatus.SUCCESS; //签收
//                }
//                return ConsumeOrderlyStatus.SUCCESS; //签收
//            }
//        });

        consumer.start();

    }
}
