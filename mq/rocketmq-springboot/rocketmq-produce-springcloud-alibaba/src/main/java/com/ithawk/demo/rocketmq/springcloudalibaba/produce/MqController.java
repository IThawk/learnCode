package com.ithawk.demo.rocketmq.springcloudalibaba.produce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {

    @Autowired
    private SenderService senderService;

    @GetMapping("/test")
    public String testSendMq(){

        try {
            senderService.send("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
