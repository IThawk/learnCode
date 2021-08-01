package com.ithawk.demo.rabbitmq.v1.ack;


import com.ithawk.demo.rabbitmq.v1.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @className AckProducer
 * @description:  消息生产者，用于测试消费者手工应答和重回队列
 * @author IThawk
 * @date 2021/8/1 21:53
 */
public class AckProducer {
    private final static String QUEUE_NAME = "TEST_ACK_QUEUE";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();

        //设置
        factory.setHost("192.168.56.101");
        factory.setPort(5672);
        //设置vhost
        factory.setVirtualHost("/my_vhost");
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String msg = "test ack message ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        // String exchange, String routingKey, BasicProperties props, byte[] body
        for (int i =0; i<5; i++){
            channel.basicPublish("", QUEUE_NAME, null, (msg+i).getBytes());
        }

        channel.close();
        conn.close();
    }
}

