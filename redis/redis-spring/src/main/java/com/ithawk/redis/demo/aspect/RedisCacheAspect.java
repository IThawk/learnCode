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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class RedisCacheAspect {


    @Autowired
    RedisTemple redisTemple;


    //    @Pointcut("@annotation(com.ithawk.redis.demo.aspect.RedisCache)")
    @Pointcut("execution(* com.ithawk.redis.demo.service..*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Method s = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] objects = joinPoint.getArgs();
        String key = AnnotationUtils.findAnnotation(s, RedisCache.class).key();
        Class clazz = AnnotationUtils.findAnnotation(s, RedisCache.class).clazz();
        int existTime = AnnotationUtils.findAnnotation(s, RedisCache.class).existTime();
        if (objects.length > 0) {
            //todo
            key += objects[0].toString();
        }
        Object user = redisTemple.get(key, clazz);
        if (user != null) {
            return user;
        }
        try {
            Object object = ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            redisTemple.setEx(key, object, existTime);
            return object;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
        return null;
    }
}
