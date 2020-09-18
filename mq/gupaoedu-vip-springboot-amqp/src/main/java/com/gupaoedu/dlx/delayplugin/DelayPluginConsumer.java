package com.gupaoedu.dlx.delayplugin;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DelayPluginConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@192.168.8.133:5672");
        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("收到消息：[" + msg + "]\n接收时间：" +sf.format(new Date()));
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume("GP_DELAY_QUEUE", true, consumer);
    }
}