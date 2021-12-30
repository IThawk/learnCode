package com.ithawk.demo.spring.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SomeConsumer {

    @KafkaListener(topics = "${kafka.topic}")
    public void onMsg(String message) {
        log.info("Kafka消费者接受到" + "消息 " + message);
    }

    @KafkaListener(topics = "hdfs_topic")
    public void onTxMsg1(String message) {
        log.info("Kafka消费者接受到" + "hdfs‐topic" + "消息 " + message);
    }

    @KafkaListener(topics = "es_topic")
    public void onTxMsg2(String message) {
        log.info("Kafka消费者接受到" + "es‐topic" + "消息 " + message);
    }


    @KafkaListener(groupId = "testGroup",
            topicPartitions = {
            @TopicPartition(topic = "es_topic", partitions = {"0", "1"}),
            @TopicPartition(topic = "redis_topic", partitions = "0",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
            }, concurrency = "6")
    public void onTxMsg3(String message) {
        log.info("Kafka消费者接受到" + "redis‐topic" + "消息 " + message);
    }

    /**
     * @param record
     * @KafkaListener(groupId = "testGroup", topicPartitions = {
     * @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
     * @TopicPartition(topic = "topic2", partitions = "0",
     * partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
     * },concurrency = "6")
     * //concurrency就是同组下的消费者个数，就是并发消费数，必须小于等于分区总数
     */
    @KafkaListener(topics = "my-replicated-topic1", groupId = "group1")
    public void listenGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
    }

    /*//配置多个消费组
    @KafkaListener(topics = "my-replicated-topic",groupId = "group2")
    public void listenGroup1(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        ack.acknowledge();
    }*/
}
