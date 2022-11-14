package com.ithawk.demo.rocketmq.springcloudalibaba.produce.controller;


import com.ithawk.demo.rocketmq.springcloudalibaba.produce.service.SenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("MqController")
public class MqController {

    @Autowired
    private SenderService senderService;

    @GetMapping("/test")
    @ApiOperation("测试发送消息")
    public String testSendMq(){

        try {
            senderService.send("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
