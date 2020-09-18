package com.gupaoedu.dubbo.practice.practiceclient;

import com.gupaoedu.dubbo.practice.ISayHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-13:44
 */
@RestController
public class SayHelloController {

    @Reference
    ISayHelloService iSayHelloService;

    @GetMapping("/say")
    public String say(){
        return iSayHelloService.sayHello("Mic");
    }
}
