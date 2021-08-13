package com.ithawk.demo.cache.ithawk.aop;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.cache.ithawk.constant.CacheActionType;
import com.ithawk.demo.cache.ithawk.constant.ITHawkCache;
import com.ithawk.demo.cache.ithawk.listener.CacheMessage;
import com.ithawk.demo.cache.ithawk.utils.ApplicationUtil;
import com.ithawk.demo.cache.ithawk.utils.LocalCaffeineCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author llwei
 * @title: ithawk
 * @projectName cache
 * @description: TODO
 * @date 2021/8/910:41
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class ITHawkCacheAop {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Around("@annotation(com.ithawk.demo.cache.ithawk.constant.ITHawkCache)")
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取缓存
        ITHawkCache itHawkCache = methodSignature.getMethod().getAnnotation(ITHawkCache.class);
        Object[] args = proceedingJoinPoint.getArgs();
        Class<?> resultClass = methodSignature.getMethod().getReturnType();
        CacheActionType cacheType = itHawkCache.actionType();
        String methodName = itHawkCache.valueMethod();
        Class<?> clazz = itHawkCache.keyClass();
        String cacheMaker = itHawkCache.keyMaker();
        String cacheHead = itHawkCache.cacheHead();
        Object result = null;
        List<String> cacheKeys = null;
        try {

            if (!"".equals(cacheMaker)) {
                //根据规则生成key
                if (cacheMaker.contains("#")) {
                    cacheKeys = makeParam(cacheMaker, args, cacheHead);
                } else {
                    cacheKeys = Arrays.asList(cacheHead + cacheMaker);
                }
            } else {
                cacheKeys = (List<String>) ApplicationUtil.invokeMethod(clazz, methodName, args);
                cacheKeys = cacheKeys.stream().map(f -> cacheHead + f).distinct().collect(Collectors.toList());
            }

            if (cacheType.equals(CacheActionType.QUERY)) {
                result = LocalCaffeineCache.getData(cacheKeys.get(0));
                System.out.println("getf from local key " + cacheKeys.get(0) + JSON.toJSONString(result));
                if (result == null) {
                    //缓存中获取数据
                    String redisResult = redisTemplate.opsForValue().get(cacheKeys.get(0));
                    System.out.println("getf from redis key " + cacheKeys.get(0) + redisResult);
                    if (redisResult != null) {
                        try {
                            Object redis = JSON.parseObject(redisResult, resultClass);
                            System.out.println("set from local key " + cacheKeys.get(0) + JSON.toJSONString(redis));
                            LocalCaffeineCache.setData(cacheKeys.get(0), redis);
                            return redis;
                        } catch (Exception e) {
                            System.out.println("缓存有问题。。。。。");
                        }

                    }
                } else {
                    return result;
                }

            }

        } catch (Exception e) {
            System.out.println("获取缓存失败");
        }


        try {
            result = proceedingJoinPoint.proceed();
            if (!CollectionUtils.isEmpty(cacheKeys)) {
                if (cacheType.equals(CacheActionType.QUERY)) {
                    LocalCaffeineCache.setAsyncData(cacheKeys.get(0), result);
                    String redis = result == null ? "{}" : JSON.toJSONString(result);
                    redisTemplate.boundValueOps(cacheKeys.get(0)).set(redis, 5, TimeUnit.MINUTES);
                } else {
                    redisTemplate.delete(cacheKeys.get(0));
                    //修改缓存 发送广播消息
                    push(new CacheMessage("TEST", cacheKeys));
                }
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    private List<String> makeParam(String cacheMaker, Object[] args, String cacheHead) {
        String[] p = cacheMaker.split("-");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < p.length; i++) {
            String a = p[i].replaceAll("#", "");
            if ("".equals(a)) {
                stringBuilder.append(args[i]);
            } else {
                Field field = null;
                try {
                    field = args[i].getClass().getDeclaredField(a);
                    field.setAccessible(true);
                    try {
                        stringBuilder.append(field.get(args[i]));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }

        }
        return Arrays.asList(cacheHead + stringBuilder.toString());
    }

    /**
     * @param message
     * @description: 缓存变更时通知其他节点清理本地缓存
     * @return: void
     * @author IThawk
     * @date: 2021/8/1 21:11
     */
    private void push(CacheMessage message) {
        redisTemplate.convertAndSend("TOPIC", JSON.toJSONString(message));
    }
}
