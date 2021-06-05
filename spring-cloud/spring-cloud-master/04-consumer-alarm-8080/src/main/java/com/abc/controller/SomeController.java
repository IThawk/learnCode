package com.abc.controller;

import com.abc.bean.Depart;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/consumer/depart")
public class SomeController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    // 创建一个线程池，包含5个线程
    private ForkJoinPool pool = new ForkJoinPool(5);

    // 直连提供者
    // private static final String SERVICE_PROVIDER = "http://localhost:8081";
    // 要使用微服务名称来从eureka server查找提供者
    private static final String SERVICE_PROVIDER = "http://abcmsc-provider-depart";
    @PostMapping("/save")
    public boolean saveHandler(@RequestBody Depart depart) {

        String url = SERVICE_PROVIDER + "/provider/depart/save";
        return restTemplate.postForObject(url, depart, Boolean.class);
    }

    @DeleteMapping("/del/{id}")
    public void deleteHandler(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/del/" + id;
        restTemplate.delete(url);
    }

    @PutMapping("/update")
    public void updateHandler(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/update";
        restTemplate.put(url, depart);
    }

    @HystrixCommand(fallbackMethod = "getHystrixHandler")
    @GetMapping("/get/{id}")
    public Depart getByIdHandler(@PathVariable("id") int id, HttpServletRequest request) {
        String url = SERVICE_PROVIDER + "/provider/depart/get/" + id;
        return restTemplate.getForObject(url, Depart.class);
    }

    // 定义服务降级方法，即响应给客户端的备选方案
    public Depart getHystrixHandler(@PathVariable("id") int id, HttpServletRequest request) {
        // 向管理员发出警报
        String ip = request.getLocalAddr();
        String key = ip + "_getHystrixHandler";
        // 指定存放到Redis中的key为“ip_发生降级的方法名”
        alarm(key);

        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart");
        return depart;
    }

    // 降级发生后的报警
    private void alarm(String key) {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);
        String value = ops.get();
        if (value == null) {
            synchronized (this) {
                value = ops.get();
                if (value == null) {
                    // 发送短信
                    sendMsg(key);
                    value="已发生服务降级";
                    ops.set(value, 10, TimeUnit.SECONDS);
                }
            }
        }
    }

    // 使用线程池实现异步短信发送
    private void sendMsg(String key) {
        pool.submit(() -> {
            System.out.println("发送服务降级警报：" + key);
        });
    }

    @GetMapping("/list")
    public List<Depart> listHandler() {
        String url = SERVICE_PROVIDER + "/provider/depart/list";
        return restTemplate.getForObject(url, List.class);
    }
}
