package com.ithawk.demo.cache.ithawk.constant;

import java.lang.annotation.*;

/**
 * @author ithawk
 * @title: ITHawkCache
 * @projectName cache
 * @description: TODO
 * @date 2021/8/910:36
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ITHawkCache {

    /**
     * @className ITHawkCache
     * @description: 缓存头信息
     * @author IThawk
     * @date 2021/8/13 23:15
     */
    String cacheHead() default "ITHawkCache";

    /**
     * @className ITHawkCache
     * @description: 缓存的操作
     * @author IThawk
     * @date 2021/8/13 23:15
     */
    CacheActionType actionType() default CacheActionType.QUERY;

    /**
     * @className ITHawkCache
     * @description: 用于生成缓存的类
     * @author IThawk
     * @date 2021/8/13 23:16
     */
    Class<?> keyClass() default String.class;

    /**
     * @className ITHawkCache
     * @description: 生成缓存的方法
     * @author IThawk
     * @date 2021/8/13 23:16
     */
    String valueMethod() default "makeITHawkCache";

    /**
     * @className ITHawkCache
     * @description: 根据入参生成 key的规则
     * <p>
     * 单独的使用 string 直接用key
     * #     使用第一个参数
     * #id  使用第一个参数的id
     * #-#  使用第一个参数和第二个参数
     * </p>
     * @author IThawk
     * @date 2021/8/13 8:13
     */
    String keyMaker() default "";

}
