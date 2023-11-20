package com.tuling.mall.skywalkingdemo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String hello() {
        return "hello fox";
    }

    @RequestMapping("/notify")
    public String notify(@RequestBody Object obj) {
        //TODO 告警信息，给技术负责人发短信，钉钉消息，邮件，微信通知等
        System.err.println(obj.toString());
        return "notify successfully";
    }
}
