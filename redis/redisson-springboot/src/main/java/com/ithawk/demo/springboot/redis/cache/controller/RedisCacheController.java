package com.ithawk.demo.springboot.redis.cache.controller;

import com.ithawk.demo.springboot.redis.cache.model.User;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 官方文档位置
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache
 * @Cacheable: Triggers cache population.
 *
 * @CacheEvict: Triggers cache eviction.
 *
 * @CachePut: Updates the cache without interfering with the method execution.
 *
 * @Caching: Regroups multiple cache operations to be applied on a method.
 *
 * @CacheConfig: Shares some common cache-related settings at class-level.
 * @Cacheable: 将数据保存到缓存；
 * @CacheEvict: 将数据从缓存删除；失效模式
 * @CachePut: 不影响方法执行更新缓存；双写模式
 * @Caching: 组合多个缓存操作；
 * @CacheConfig: 在类级别共享缓存的相同配置；
 */
@RestController
@RequestMapping("/cache")
public class RedisCacheController {

    @GetMapping("/get")
    @Cacheable(cacheNames={"user"},key = "#root.methodName")
    public User getRedisCache() {
        User user = new  User();
        System.out.println("开始生成缓存数据");
        user.setAge(10);
        user.setName("test");
        user.setPassword("test");
        return user;
    }

    @GetMapping("/get1")
    @Cacheable(cacheNames="user",key = "#root.methodName")
    public User getRedisCache1() {
        User user = new  User();
        System.out.println("开始生成缓存数据");
        user.setAge(10);
        user.setName("test");
        user.setPassword("test");
        return user;
    }



    @GetMapping("/get2")
    @Cacheable(value="user2",key = "#root.methodName")
    public User getRedisCache2() {
        User user = new  User();
        System.out.println("开始生成缓存数据");
        user.setAge(10);
        user.setName("test2");
        user.setPassword("test2");
        return user;
    }


    @GetMapping("/up")
    @CachePut(cacheNames="user",key = "'getRedisCache'")
    public User updateRedisCache1() {
        User user = new  User();
        System.out.println("开始生成缓存数据");
        user.setAge(10);
        user.setName("testupdate");
        user.setPassword("testupdate");
        return user;
    }

    @CacheEvict(cacheNames = "user",key = "'getRedisCache'")
    @GetMapping("/del")
    public String delRedisGetRedisCache(){
        System.out.println("开始清理缓存数据");
        return "OK";
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = "user",key = "'getRedisCache'"),
            @CacheEvict(cacheNames = "user",key = "'getRedisCache1'")
    })
    @GetMapping("/delAll")
    public String delAllRedisGetRedisCache(){
        System.out.println("开始清理缓存数据");
        return "OK";
    }

    @CacheEvict(cacheNames = "user", allEntries=true)
    @GetMapping("/delUser")
    public String delAllUserRedisGetRedisCache(){
        System.out.println("开始清理缓存数据");
        return "OK";
    }
}
