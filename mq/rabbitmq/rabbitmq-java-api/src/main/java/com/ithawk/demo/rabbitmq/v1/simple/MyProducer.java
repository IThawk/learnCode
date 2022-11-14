package com.ithawk.demo.rabbitmq.v1.simple;

import com.ithawk.demo.rabbitmq.v1.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * 消息生产者
 */
public class MyProducer {
    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ResourceUtil.getKey("rabbitmq.host"));
        factory.setPort(Integer.parseInt(ResourceUtil.getKey("rabbitmq.port")));
        //设置vhost
        factory.setVirtualHost(ResourceUtil.getKey("rabbitmq.vhost"));
        factory.setUsername(ResourceUtil.getKey("rabbitmq.user"));
        factory.setPassword(ResourceUtil.getKey("rabbitmq.password"));

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 发送消息
        String msg = "Hello world, Rabbit MQ";

        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(EXCHANGE_NAME, "test.best", null, msg.getBytes());

        channel.close();
        conn.close();
    }
}

