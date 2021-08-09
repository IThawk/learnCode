package com.ithawk.demo.cache.ithawk.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author IThawk
 * @className LocalCaffeineCache
 * @description:
 * @date 2021/8/1 14:46
 */
public class LocalCaffeineCache {


    private static Cache<String, Object> cache;

    private static AsyncLoadingCache<String, Object> asyncCache;

    private static final long WRITE_EXPIRE_TIMEOUT = 1;

    private static final long ACCESS_EXPIRE_TIMEOUT = 2;

    private static final long CACHE_MAX_SIZE = 10;

    //
//    expireAfterWrite：表示自从最后一次写入后多久就会过期；
//
//    expireAfterAccess：表示自从最后一次访问（写入或者读取）后多久就会过期；
//
//    expireAfter：自定义过期策略；
//    在构造Cache时通过refreshAfterWrite方法指定刷新周期，例如refreshAfterWrite(10, TimeUnit.SECONDS)表示10秒钟刷新一次：
//    .build(new CacheLoader<String, String>() {
//        @Override
//        public String load(String k) {
//            // 这里我们就可以从数据库或者其他地方查询最新的数据
//            return getValue(k);
//        }
//    });
    /**
     * @param
     * @description:
     * @return: com.github.benmanes.caffeine.cache.Cache<java.lang.String, java.lang.Object>
     * @author IThawk
     * @date: 2021/8/1 14:26
     */
    private static Cache<String, Object> getCacheObject() {
        if (cache == null) {
            synchronized (LocalCaffeineCache.class) {
                if (cache == null) {
//                    初始化缓存：
                    cache = Caffeine.newBuilder()
                            // 过期机制
                            .expireAfterWrite(WRITE_EXPIRE_TIMEOUT, TimeUnit.MINUTES)
                            .expireAfterAccess(ACCESS_EXPIRE_TIMEOUT, TimeUnit.MINUTES)
                            // 数量上限
                            .maximumSize(CACHE_MAX_SIZE)
                            // 弱引用key
                            .weakKeys()
                            // 弱引用value
                            .weakValues()
                            // 剔除监听
                            .removalListener((RemovalListener<String, Object>) (key, value, cause) ->
                                    System.out.println("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))

                            .build();
                }
            }
        }
        return cache;
    }


    private static AsyncLoadingCache<String, Object> getAsyncCacheObject() {
        if (asyncCache == null) {
            synchronized (LocalCaffeineCache.class) {
                if (asyncCache == null) {
//                    初始化缓存：
                    asyncCache = Caffeine.newBuilder()
                            .maximumSize(100)
                            .expireAfterWrite(1, TimeUnit.MINUTES)
                            .buildAsync(k -> {
                                return "Data for " + k;
                            });
                }
            }
        }
        return asyncCache;
    }

    /**
     * 获取缓存
     * 未获取到 返回 null
     *
     * @param key
     * @return
     */
    public static Object getData(String key) {
        //getIfPresent 方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        return getCacheObject().getIfPresent(key);
    }

    /**
     * 异步获取缓存
     * 未获取到 返回 null
     *
     * @param key
     * @return
     */
    public static CompletableFuture<Object> getAsyncData(String key) {
        //getIfPresent 方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        return getAsyncCacheObject().getIfPresent(key);
    }

    /**
     * @param key
     * @description: 批量获取数据
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @author IThawk
     * @date: 2021/8/1 15:18
     */
    public static Map<String, Object> getData(List<String> key) {
        //getIfPresent 方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        return getCacheObject().getAllPresent(key);
    }

    /**
     * @param key
     * @param f   传入的方法
     * @description: 获取缓存，没有获取到缓存，执行默认方法
     * @return: java.lang.Object
     * @author IThawk
     * @date: 2021/8/1 15:08
     */
    public static Object getDataWithDefault(String key, Function<String, Object> f) {
//        使用 get 方法获取值，该方法将一个参数为 key 的 Function 作为参数传入。
//        如果缓存中不存在该 key，则该函数将用于提供默认值，该值在计算后插入缓存中：
//        get 方法可以以原子方式执行计算。这意味着你只进行一次计算 —— 即使有多个线程同时请求该值。这就是为什么使用 get 要优于 getIfPresent
        return getCacheObject().get(key, f);
    }

    /**
     * @param key
     * @description: 去除缓存的key
     * @return: void
     * @author IThawk
     * @date: 2021/8/1 14:49
     */
    public static void removeDataByKey(String key) {
//        手动触发一些缓存的值失效：
        getCacheObject().invalidate(key);
    }

    /**
     * @param key
     * @param data
     * @description: 设置缓存
     * @return: void
     * @author IThawk
     * @date: 2021/8/1 14:25
     */
    public static void setData(String key, Object data) {
        //使用 put 方法手动填充缓存：
        getCacheObject().put(key, data);
    }


    /**
     * @param key
     * @param data
     * @description: 设置缓存
     * @return: void
     * @author IThawk
     * @date: 2021/8/1 14:25
     */
    public static void setAsyncData(String key, Object data) {
        CompletableFuture<Object> f = new CompletableFuture();
        f.complete(data);
        //使用 put 方法手动填充缓存：
        getAsyncCacheObject().put(key, f);
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
    //基于时间回收
    //这种回收策略是基于条目的到期时间，有三种类型：
    //
    //访问后到期 — 从上次读或写发生后，条目即过期。
    //写入后到期 — 从上次写入发生之后，条目即过期
    //自定义策略 — 到期时间由 Expiry 实现独自计算
    //
    //让我们使用 expireAfterAccess 方法配置访问后过期策略：
    //LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
    //  .expireAfterAccess(5, TimeUnit.MINUTES)
    //  .build(k -> DataObject.get("Data for " + k));
    //复制代码要配置写入后到期策略，我们使用 expireAfterWrite 方法：
    //cache = Caffeine.newBuilder()
    //  .expireAfterWrite(10, TimeUnit.SECONDS)
    //  .weakKeys()
    //  .weakValues()
    //  .build(k -> DataObject.get("Data for " + k));
    //复制代码要初始化自定义策略，我们需要实现 Expiry 接口：
    //cache = Caffeine.newBuilder().expireAfter(new Expiry<String, DataObject>() {
    //    @Override
    //    public long expireAfterCreate(
    //      String key, DataObject value, long currentTime) {
    //        return value.getData().length() * 1000;
    //    }
    //    @Override
    //    public long expireAfterUpdate(
    //      String key, DataObject value, long currentTime, long currentDuration) {
    //        return currentDuration;
    //    }
    //    @Override
    //    public long expireAfterRead(
    //      String key, DataObject value, long currentTime, long currentDuration) {
    //        return currentDuration;
    //    }
    //}).build(k -> DataObject.get("Data for " + k));
    //
    //自动刷新
//        LoadingCache<String, Object> cache1 = Caffeine.newBuilder()
//                .maximumWeight(10000)
//        .refreshAfterWrite(1,TimeUnit.SECONDS)
//                .weigher(key -> function1(key))
//                .build(key -> function(key));

}
