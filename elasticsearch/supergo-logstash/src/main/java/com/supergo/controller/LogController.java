package com.supergo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    public static Logger logger = LoggerFactory.getLogger(LogController.class);
    @RequestMapping("/hello")
    public Object sayHello(String name) {
        logger.debug("开始执行sayHello方法");
        logger.info("hello:" + name);
        logger.debug("sayHello方法执行完毕");
        return "OK";
    }
}
