package com.ithawk.demo.dubbo.spi.practice.practiceclient;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dubbo-spi
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
