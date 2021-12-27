package com.ithawk.demo.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * 创建事务消息
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("GroupTransaction");
        //2.指定nameserver地址
        producer.setNamesrvAddr("192.168.56.101:9876");
        //3.添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            //在提交完事务消息后执行。
            //返回COMMIT_MESSAGE状态的消息会立即被消费者消费到。
            //返回ROLLBACK_MESSAGE状态的消息会被丢弃。
            //返回UNKNOWN状态的消息会由Broker过一段时间再来回查事务的状态。
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg,
                                                                 Object arg) {
                String tags = msg.getTags();
                //TagA的消息会立即被消费者消费到
                if (StringUtils.contains(tags, "TagA")) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                    //TagB的消息会被丢弃
                } else if (StringUtils.contains(tags, "TagB")) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
//其他消息会等待Broker进行事务状态回查。
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            //在对UNKNOWN状态的消息进行状态回查时执行。返回的结果是一样的。
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String tags = msg.getTags();
                //TagC的消息过一段时间会被消费者消费到
                if (StringUtils.contains(tags, "TagC")) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                    //TagD的消息也会在状态回查时被丢弃掉
                } else if (StringUtils.contains(tags, "TagD")) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                    //剩下TagE的消息会在多次状态回查后最终丢弃
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }
        });

        //4.启动producer
        producer.start();
        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 0; i < 3; i++) {
            //5.创建消息对象，制定topic、tag 和 消息体
            Message msg = new Message("TransactionTopic", tags[i],
                    ("Hello RocketMQ: " + tags[i]).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            //6.发送消息
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            //7.获取发送状态
            SendStatus sendStatus = sendResult.getSendStatus();
            System.out.printf("发送结果:%s%n", sendStatus);
            TimeUnit.SECONDS.sleep(1);
        }
        //8.关闭生产者producer
//        producer.shutdown();
    }
}
