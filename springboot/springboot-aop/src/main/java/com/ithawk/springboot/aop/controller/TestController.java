package com.ithawk.springboot.aop.controller;

import com.ithawk.springboot.aop.aspect.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @Test(id = "ddd",className="com.ithawk.springboot.aop.service.TestServiceImpl",strings = {"1","2"})
    public String Test(@RequestParam(name = "id")String id){
        System.out.println(id);
        return id;
    }
}
