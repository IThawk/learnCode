package com.ithawk.demo.cache.caffeine;


import com.github.benmanes.caffeine.cache.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class DemoApplication {


    private static Cache<String, Object> cache;

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .build();
    }

    public static void main(String[] args) {
        manulOperator("test1", "ssss");
        System.out.println(get("test1"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(get("test1"));

    }

    /**
     * 手动加载
     *
     * @param key
     * @return
     */
    public static Object get(String key) {

        //如果一个key不存在，那么会进入指定的函数生成value
//        Object value = cache.get(key, t -> setValue(key,o).apply(key));
        Object ifPresent = cache.getIfPresent(key);
        System.out.println(ifPresent);
        if (ifPresent == null) {
            return null;
        }
        //
        Object o = cache.get(key, f -> f);

//        //判断是否存在如果不存返回null

//        //移除一个key
//        cache.invalidate(key);
        return o;
    }

    /**
     * 手动加载
     *
     * @param key
     * @return
     */
    public static Object manulOperator(String key, Object o) {

        //如果一个key不存在，那么会进入指定的函数生成value
//        Object value = cache.get(key, t -> setValue(key,o).apply(key));
        cache.put(key, o);

//        //判断是否存在如果不存返回null
//        Object ifPresent = cache.getIfPresent(key);
//        //移除一个key
//        cache.invalidate(key);
        return o;
    }

    /**
     * 手动加载
     *
     * @param key
     * @return
     */
    public static Object manulOperator(String key) {

        //如果一个key不存在，那么会进入指定的函数生成value
        Object value = cache.get(key, t -> setValue(key).apply(key));
        cache.put("hello", value);

        //判断是否存在如果不存返回null
        Object ifPresent = cache.getIfPresent(key);
        //移除一个key
        cache.invalidate(key);
        return value;
    }

    public static Function<String, Object> setValue(String key, Object o) {
        return t -> key + o;
    }

    public static Function<String, Object> setValue(String key) {
        return t -> key + "o";
    }


    /**
     * 同步加载
     *
     * @param key
     * @return
     */
    public static Object syncOperator(String key) {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> setValue(key).apply(key));
        return cache.get(key);
    }


    /**
     * 异步加载
     *
     * @param key
     * @return
     */
    public static Object asyncOperator(String key) {
        AsyncLoadingCache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(k -> setAsyncValue(key).get());

        return cache.get(key);
    }

    public static CompletableFuture<Object> setAsyncValue(String key) {
        return CompletableFuture.supplyAsync(() -> {
            return key + "value";
        });
    }


//    //基于大小的回收策略有两种方式：一种是基于缓存大小，一种是基于权重。
//
//    public void ss(){
//        // 根据缓存的计数进行驱逐
//        LoadingCache<String, Object> cache = Caffeine.newBuilder()
//                .maximumSize(10000)
//                .build(key -> function(key));
//
//
//        // 根据缓存的权重来进行驱逐（权重只是用于确定缓存大小，不会用于决定该缓存是否被驱逐）
//        LoadingCache<String, Object> cache1 = Caffeine.newBuilder()
//                .maximumWeight(10000)
//                .weigher(key -> function1(key))
//                .build(key -> function(key));
//        //maximumWeight与maximumSize不可以同时使用。
//    }
//    public void ss1() {
//
//        //  2.基于时间的过期方式
//        // 基于固定的到期策略进行退出
//        LoadingCache<String, Object> cache = Caffeine.newBuilder()
//                .expireAfterAccess(5, TimeUnit.MINUTES)
//                .build(key -> function(key));
//        LoadingCache<String, Object> cache1 = Caffeine.newBuilder()
//                .expireAfterWrite(10, TimeUnit.MINUTES)
//                .build(key -> function(key));
//    }
//
//    public void ss2() {
//        // 基于不同的到期策略进行退出
//        LoadingCache<String, Object> cache2 = Caffeine.newBuilder()
//                .expireAfter(new Expiry<String, Object>() {
//                    @Override
//                    public long expireAfterCreate(String key, Object value, long currentTime) {
//                        return TimeUnit.SECONDS.toNanos(seconds);
//                    }
//
//                    @Override
//                    public long expireAfterUpdate(@Nonnull String s, @Nonnull Object o, long l, long l1) {
//                        return 0;
//                    }
//
//                    @Override
//                    public long expireAfterRead(@Nonnull String s, @Nonnull Object o, long l, long l1) {
//                        return 0;
//                    }
//                }).build(key -> function(key));
//    }
}
