package com.ithawk.mybatis.demo.sentinel.sentinelweb;

import com.ithawk.mybatis.demo.sentinel.SentinelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/10-20:28
 */
@RestController
public class SentinelController {

    @Reference(timeout = 3000)
    SentinelService sentinelService;//proxy$0

    @GetMapping("/say")
    public String sayHello(){
        return sentinelService.sayHello("test");
    }
}
