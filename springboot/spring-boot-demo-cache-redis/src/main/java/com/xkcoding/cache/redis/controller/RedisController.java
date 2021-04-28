package com.xkcoding.cache.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {


    @GetMapping( "/test")
    public String get(){
        return " ";
    }

}
