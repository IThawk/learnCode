package com.gupaoedu.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/17-20:22
 */
public class GpKafkaProducer extends Thread{

    //producer api
    KafkaProducer<Integer,String> producer;
    String topic;  //主题

    public GpKafkaProducer(String topic) {
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.13.102:9092,192.168.13.103:9092,192.168.13.104:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"gp-producer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.gupaoedu.kafka.MyPartition");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //连接的字符串
        //通过工厂
        //new
        producer=new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }
    @Override
    public void run() {
        int num=0;
        while(num<20) {
            try {
                String msg="gp kafka practice msg:"+num;
                //get 会拿到发送的结果
                //同步 get() -> Future()
                //回调通知
                producer.send(new ProducerRecord<>(topic, msg), (metadata, exception) -> {

                    System.out.println(metadata.offset()+"->"+metadata.partition()+"->"+metadata.topic());
                });
                TimeUnit.SECONDS.sleep(2);
                ++num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GpKafkaProducer("test_partition").start();
    }
}
