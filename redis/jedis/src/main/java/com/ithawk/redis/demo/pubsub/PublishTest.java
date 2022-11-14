package com.ithawk.redis.demo.pubsub;

import redis.clients.jedis.Jedis;

/**
 *
 */
public class PublishTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.56.101", 6379);
        jedis.publish("test-123", "666");
        jedis.publish("test-abc", "pengyuyan");
    }
}
