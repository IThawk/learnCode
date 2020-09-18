package com.gupaoedu.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/17-21:50
 */
public class MyPartition implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        System.out.println("enter");
        List<PartitionInfo> list=cluster.partitionsForTopic(topic);
        int leng=list.size();
        if(key==null){
            Random random=new Random();
            return random.nextInt(leng);
        }
        return Math.abs(key.hashCode())%leng;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    public static void main(String[] args) {
        System.out.println(Math.abs("gp-gid1".hashCode())%50);
    }
}
