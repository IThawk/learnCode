package com.ithawk.redis.demo.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;

import java.util.Map;

/**
 */
public class ListenTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.56.101", 6379);

//        添加订阅监听
        final MyListener listener = new MyListener();
        // 使用模式匹配的方式设置频道
        // 会阻塞
        jedis.psubscribe(listener, new String[]{"test-*"});

    }


}
