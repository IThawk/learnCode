package com.ithawk.demo.spring.v1.dubbo.dubboclient;

import com.ithawk.demo.spring.v1.dubbo.ISayHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/21-20:42
 */
@RestController
public class DubboController {

    //Dubbo提供的注解 设置超时未1秒
    @Reference(loadbalance = "roundrobin", timeout = 1, cluster = "failfast", mock = "com.gupaoedu.dubbo.dubboclient.SayHelloServiceMock", check = false)
    ISayHelloService sayHelloService; //dubbo://

    @GetMapping("/sayhello")
    public String sayHello() throws InterruptedException {
        return sayHelloService.sayHello(); //我调用这个服务可能失败，如果失败了，我要怎么处理
    }


}
