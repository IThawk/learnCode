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
        ConcurrentHashMap<String,String> concurrentHashMap= new ConcurrentHashMap<>();
        concurrentHashMap.put("test","test");
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

    }
}
