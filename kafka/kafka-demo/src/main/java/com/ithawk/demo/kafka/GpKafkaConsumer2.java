package com.ithawk.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class GpKafkaConsumer2 extends Thread{

    KafkaConsumer<Integer,String> consumer;
    String topic;

    public GpKafkaConsumer2(String topic) {

        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"my-consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"my-gid1");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000"); //自动提交(批量确认)
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false); //关闭自动提交
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //一个新的group的消费者去消费一个topic
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest"); //这个属性. 它能够消费昨天发布的数据
        consumer = new KafkaConsumer<Integer, String>(properties);
        consumer.partitionsFor("test_0");
        this.topic = topic;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(this.topic));
        List<ConsumerRecord> batchSize=new ArrayList<>();
        while(true){
            ConsumerRecords<Integer,String> consumerRecords=consumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(record->{
                //null->gp kafka practice msg:0->63
                System.out.println(record.key()+"->"+record.value()+"->"+record.offset());
                batchSize.add(record);
            });
            if(batchSize.size()>=200){
               ; //同步提交
                batchSize.clear();
            }
        }
    }

    public static void main(String[] args) {
        new GpKafkaConsumer2("test_partition").start();
    }
}
