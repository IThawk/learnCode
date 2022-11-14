package com.ithawk.redis.demo.datatype;

import redis.clients.jedis.Jedis;

/**
 * redis 操作String
 */
public class BasicTest {
    public static void main(String[] args) {
        //创建一个jedis 连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("test", "2673");
        System.out.println(jedis.get("test"));
        jedis.close();
    }
}
