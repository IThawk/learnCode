package com.ithawk.demo.springboot.cache.plugin.properties;

import lombok.Data;

/**
 * @author IThawk
 * @className CaffeineConfigProp
 * @description:
 * @date 2021/8/1 17:34
 */
@Data
public class CaffeineConfigProp {


/**
 spring:
   cache:
     multi:
       cachePrefix: test
     caffeine:
       expire-after-access: 1000
       maximumsize: 1000
       initial-capacity: 500
 */
    /**
     * 访问后过期时间，单位毫秒
     */
    private long expireAfterAccess;

    /**
     * 写入后过期时间，单位毫秒
     */
    private long expireAfterWrite;

    /**
     * 写入后刷新时间，单位毫秒
     */
    private long refreshAfterWrite;

    /**
     * 初始化大小
     */
    private int initialCapacity;

    /**
     * 最大缓存对象个数，超过此数量时之前放入的缓存将失效
     */
    private long maximumSize;

}
