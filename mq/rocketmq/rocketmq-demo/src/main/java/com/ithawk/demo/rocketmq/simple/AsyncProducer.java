package com.ithawk.demo.rocketmq.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步发送消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.56.101:9876");
        //Launch the instance.
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 1;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        ("Hello world "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //设置异步发送mq消息
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                            countDownLatch.countDown();
                            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                        }

                        @Override
                        public void onException(Throwable e) {
                            countDownLatch.countDown();
                            System.out.printf("%-10d Exception %s %n", index, e);
                            e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
