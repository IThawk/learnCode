package com.ithawk.demo.cache.caffeine;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

public class CaffeineDemo {


    public static void main(String[] args) {
        LocalCaffeineCache.setData("test1", "ssss");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalCaffeineCache.getData("test1"));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠后");
        System.out.println(LocalCaffeineCache.getData("test1"));


        for (int i = 0; i < 20; i++) {
            LocalCaffeineCache.setData("test" + i, "ssss");
        }
        System.out.println("设置多个数后");
        System.out.println(LocalCaffeineCache.getData("test9"));
        System.out.println(LocalCaffeineCache.getData("test19"));


        System.out.println(LocalCaffeineCache.getDataWithDefault("test29", (key) -> {
            return "not get cache key " + key;
        }));

        System.out.println(JSON.toJSONString(LocalCaffeineCache.getData(Arrays.asList("test1","test2","test23"))));
        LocalCaffeineCache.setAsyncData("tt","t1233");
        System.out.println(LocalCaffeineCache.getAsyncData("tt").thenAccept(dataObject -> {
            System.out.println(dataObject);
            System.out.println("------");
        }));
    }


}
