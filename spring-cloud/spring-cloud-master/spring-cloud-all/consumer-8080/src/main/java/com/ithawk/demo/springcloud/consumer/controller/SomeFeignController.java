package com.ithawk.demo.springcloud.consumer.controller;

import com.ithawk.demo.springcloud.consumer.bean.Depart;
import com.ithawk.demo.springcloud.consumer.service.DepartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consumer/depart/feign")
public class SomeFeignController {
    @Autowired
    private RestTemplate restTemplate;
    // 直连提供者
    // private static final String SERVICE_PROVIDER = "http://localhost:8081";
    // 要使用微服务名称来从eureka server查找提供者
    private static final String SERVICE_PROVIDER = "http://abcmsc-provider-depart";

    @Autowired
    DepartService departService;

    // 定义服务降级方法，即响应给客户端的备选方案

    // 指定该方法要使用服务降级。即当前处理器方法在运行过程中若发生异常，
    // 无法给客户端正常响应时，就会调用fallbackMethod指定的方法

    public Depart getHystrixHandler(@PathVariable("id") int id) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart -- method");
        return depart;
    }


    @PostMapping("/save")
    public boolean saveHandler(@RequestBody Depart depart) {

        return departService.saveDepart(depart);
    }

    @DeleteMapping("/del/{id}")
    public void deleteHandler(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/del/" + id;
        departService.getDepartById(id);
    }

    @PutMapping("/update")
    public void updateHandler(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/update";
        departService.modifyDepart(depart);
    }

    @HystrixCommand(fallbackMethod = "getHystrixHandler",
            //cssj
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "2000"))

    @GetMapping("/get/{id}")
    public Depart getByIdHandler(@PathVariable("id") int id) {
        log.info("调用consumer的getByIdHandler()方法");
        String url = SERVICE_PROVIDER + "/provider/depart/get/" + id;
        return departService.getDepartById(id);
    }

    @GetMapping("/list")
    public List<Depart> listHandler() {
        String url = SERVICE_PROVIDER + "/provider/depart/list";
        return departService.listAllDeparts();
    }
}
