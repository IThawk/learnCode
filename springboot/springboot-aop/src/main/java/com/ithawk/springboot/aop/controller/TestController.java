package com.ithawk.springboot.aop.controller;

import com.ithawk.springboot.aop.aspect.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 日志
     */
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    @Test(id = "ddd", className = "com.ithawk.springboot.aop.service.TestServiceImpl", strings = {"1", "2"})
    public String Test(@RequestParam(name = "id") String id) {
        logger.info("com.ithawk.springboot.aop.controller.TestController.Test:{}", id);
        return id;
    }
}
