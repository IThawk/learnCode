package com.ithawk.demo.spring.v1.example.order.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/order")
    public String order() {
        System.out.println("开始下单");
        //扣减库存
        //httpclient  RestTemplate  httpclient（）
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "http://localhost:8081/repo/{1}";
        restTemplate.put(url, null, 10001);
        return "SUCCESS";
    }
}
