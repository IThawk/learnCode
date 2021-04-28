package com.xkcoding.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xkcoding.dubbo.common.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Hello服务API
 * </p>
 *
 * @package: com.xkcoding.dubbo.consumer.controller
 * @description: Hello服务API
 * @author: yangkai.shen
 * @date: Created in 2018-12-25 17:22
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@Slf4j
public class HelloController {
    @Reference
    private HelloService helloService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam(defaultValue = "xkcoding") String name) {
        log.info("i'm ready to call someone......");
        return helloService.sayHello(name);
    }

    @GetMapping("/sayHello1")
    public String sayHello1(@RequestParam(defaultValue = "xkcoding") String name) {
        log.info("i'm ready to call someone......");
        return helloService.sayMy(new Mytset("test"));
    }

    @GetMapping("/sayHello12")
    public String sayHello12(@RequestParam(defaultValue = "xkcoding") String name) {
        log.info("i'm ready to call someone......");
        List<Mytset> list = new ArrayList<>();
        list.add(new Mytset("test1"));
        list.add(new Mytset("test2"));
        list.add(new Mytset("test3"));
        list.add(new Mytset("test4"));
        return helloService.sayMy(list);
    }
}
