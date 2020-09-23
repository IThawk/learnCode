package com.ithawk.redis.demo.aspect;

import com.ithawk.redis.demo.User;
import com.ithawk.redis.demo.temple.RedisTemple;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class RedisCacheAspect {


    @Autowired
    RedisTemple redisTemple;


    @Pointcut("@annotation(RedisCache)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Method s = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] objects = joinPoint.getArgs();
        String key = AnnotationUtils.findAnnotation(s, RedisCache.class).key();
        if (objects.length > 0) {
            key += objects[0].toString();
        }

        Object user = redisTemple.get(key);
        if (user != null) {
            return user;
        }
        try {
            Object object = ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            redisTemple.set(key, object);
            return object;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
        return null;
    }
}
