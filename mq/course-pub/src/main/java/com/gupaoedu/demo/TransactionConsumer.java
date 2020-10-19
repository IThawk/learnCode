package com.gupaoedu.demo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/31-22:07
 */
public class TransactionConsumer {

    //rocketMQ 除了在同一个组和不同组之间的消费者的特性和kafka相同之外
    //RocketMQ可以支持广播消息，就意味着，同一个group的每个消费者都可以消费同一个消息


    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer=
                new DefaultMQPushConsumer("tx_consumer");
        defaultMQPushConsumer.setNamesrvAddr("192.168.13.102:9876");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //subExpression 可以支持sql的表达式. or  and  a=? ,,,
        defaultMQPushConsumer.subscribe("order_tx_topic","*");
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.stream().forEach(message->{
                    //扣减库存
                    System.out.println("开始业务处理逻辑：消息体："+new String(message.getBody())+"->key:"+message.getKeys());
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //签收
            }
        });
        defaultMQPushConsumer.start();
    }

}
