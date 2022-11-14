package com.ithawk.demo.rocketmq.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FilterBySQLConsumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // Specify name server addresses.
        consumer.setNamesrvAddr("localhost:9876");

        consumer.subscribe("TopicTest", MessageSelector.bySql("age between 0 and 6"));
        consumer.registerMessageListener(new MessageListenerConcurrently(){

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> paramList,
                                                            ConsumeConcurrentlyContext paramConsumeConcurrentlyContext) {
                try {
                    for(MessageExt msg : paramList){
                        String msgbody = new String(msg.getBody(), "utf-8");
                        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                        Date date = new Date(msg.getStoreTimestamp());
                        System.out.println("Consumer===  存入时间 :  "+ sd.format(date) +" == MessageBody: "+ msgbody);//输出消息内容
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
            }
        });
        consumer.start();
        System.out.println("Consumer===启动成功!");
    }
}
