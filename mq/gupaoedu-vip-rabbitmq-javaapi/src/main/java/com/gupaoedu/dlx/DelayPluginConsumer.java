package com.gupaoedu.dlx;

import com.gupaoedu.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qingshan
 * @Date: 2019/01/14 21:56
 * @Description: 咕泡学院，只为更好的你
 *  使用延时插件实现的消息投递-消费者
 *  必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 *  先启动消费者
 */
public class DelayPluginConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://admin:admin@192.168.8.150:5672");
        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 声明x-delayed-message类型的exchange
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-delayed-type", "direct");
        channel.exchangeDeclare("DELAY_EXCHANGE", "x-delayed-message", false,
                false, argss);

        // 声明队列
        channel.queueDeclare("DELAY_QUEUE", false,false,false,null);

        // 绑定交换机与队列
        channel.queueBind("DELAY_QUEUE", "DELAY_EXCHANGE", "DELAY_KEY");

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
        channel.basicConsume("DELAY_QUEUE", true, consumer);
    }
}