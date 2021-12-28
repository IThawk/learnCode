package com.ithawk.demo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class KafkaProducer extends Thread {

    //producer api
    org.apache.kafka.clients.producer.KafkaProducer<Integer, String> producer;
    String topic;  //主题

    public KafkaProducer(String topic) {
        //kafka 配置
        Properties properties = new Properties();
        //配置kafka的地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        //设置clientId
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "test-producer");
        //设置分区策略配置
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.ithawk.demo.kafka.MyPartition");
        //设置序列化配置
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //连接的字符串
        //通过工厂
        //new
        producer = new org.apache.kafka.clients.producer.KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        int num = 0;
        while (num < 20) {
            try {
                String msg = " kafka practice msg:" + num;
                //get 会拿到发送的结果
                //同步 get() -> Future()
                //异步发送消息
                producer.send(new ProducerRecord<>(topic, msg), (metadata, exception) -> {

                    //设置回调函数
                    //metadata : offset:消息的偏移 partition:分区 topic：主题
                    System.out.println("offset:"+metadata.offset() + "->partition:" + metadata.partition() + "->topic:" + metadata.topic());
                });
                TimeUnit.SECONDS.sleep(2);
                ++num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new KafkaProducer("test_partition").start();
    }
}
