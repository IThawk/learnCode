package com.ithawk.demo.thread.all;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        map.put("key1", "1");
        map.put("key2", "2");
        map.put("key3", "3");
        map.put("key4", "4");
        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key + ","+ map.get(key));

        }
    }

}
