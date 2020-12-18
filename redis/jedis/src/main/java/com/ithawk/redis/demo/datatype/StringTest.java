package com.ithawk.redis.demo.datatype;

import com.ithawk.redis.demo.util.JedisUtil;

/**
 *
 */
public class StringTest {
    public static void main(String[] args) {
        JedisUtil.getJedisUtil().set("test", "2673");
        // JedisUtil.getJedisUtil().incr("test");

        String qs = JedisUtil.getJedisUtil().get("test");
        System.out.println(qs);


    }
}
