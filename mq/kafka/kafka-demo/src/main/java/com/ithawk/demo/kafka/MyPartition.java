package com.ithawk.demo.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 自定义kafka分区实现
 */
public class MyPartition implements Partitioner {

    /**
     * 自定义实现：随机分配分区
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        System.out.println("partition enter");
        List<PartitionInfo> list = cluster.partitionsForTopic(topic);
        int leng = list.size();
        if (key == null) {
            Random random = new Random();
            return random.nextInt(leng);
        }
        return Math.abs(key.hashCode()) % leng;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    public static void main(String[] args) {
        System.out.println(Math.abs("my-gid1".hashCode()) % 50);
    }
}
