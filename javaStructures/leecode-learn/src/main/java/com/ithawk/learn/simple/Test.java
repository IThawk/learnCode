package com.ithawk.learn.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("test1");
        stringList.add("test1");
        stringList.add("test2");
        stringList.add("test3");
        stringList.add("test1");

//        Map<String,String> s = stringList.stream().collect(Collectors.toMap(String::toLowerCase,String::toString));
//        System.out.println(s.toString());
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("test", "test");
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        Map<String, String> map = new HashMap<>();
        map.put("test", "test0001");
        map.put("test2", "test0002");
        map.put("test3", "test0003");
        Map<String, String> map1 = new HashMap<>();
        map1.put("test", "test1");
        map1.put("test4", "test2");
        map1.put("test5", "test3");

        map.forEach((key, value) -> {
            if (map1.containsKey(key)) {
//                value = ;

                map.put(key, map1.get(key));
                map1.remove(key);

            }
            if (key.equals("test")){
                return;
            }
            System.out.println("shuc---"+key);
        });

        map.putAll(map1);

        System.out.println(map.toString());

    }
}
