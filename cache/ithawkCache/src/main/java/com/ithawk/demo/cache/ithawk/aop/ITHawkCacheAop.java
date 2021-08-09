package com.ithawk.demo.cache.ithawk.aop;

import com.ithawk.demo.cache.ithawk.constate.ITHawkCache;
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

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate<String, Object> redisTemplate;


    @Around("@annotation(com.ithawk.demo.cache.ithawk.constate.ITHawkCache)")
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取缓存
        ITHawkCache itHawkCache = methodSignature.getMethod().getAnnotation(ITHawkCache.class);
        Object[] args = proceedingJoinPoint.getArgs();
        String cacheType = itHawkCache.actionType();
        String methodName = itHawkCache.valueMethod();
        Class<?> clazz = itHawkCache.keyClass();
        List<String> cacheKeys = (List<String>) ApplicationUtil.invokeMethod(clazz, methodName, args);
        Object result = null;
        if (cacheType.contains("QUERY")) {

            result = LocalCaffeineCache.getData(cacheKeys.get(0));
            if (result == null) {
                //缓存中获取数据
                result = redisTemplate.opsForValue().get(cacheKeys.get(0));
                if (result != null) {
                    LocalCaffeineCache.setData(cacheKeys.get(0), result);
                    return result;
                }
            }else {
                return result;
            }


        }

        try {
            result = proceedingJoinPoint.proceed();
            LocalCaffeineCache.setAsyncData(cacheKeys.get(0), result);
            redisTemplate.boundValueOps(cacheKeys.get(0)).set(result,5, TimeUnit.MINUTES);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
