package com.ithawk.demo.springcloud.consumer.configure;

import com.ithawk.demo.springcloud.consumer.balance.CustomRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DepartCodeConfigure {

    // 负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 修改负载均衡策略为：随机策略
    // @Bean
    // public IRule loadBalanceRule() {
    //     return new RoundRobinRule();
    // }

//     修改负载均衡策略为：自定义策略
     @Bean
     public IRule loadBalanceRule() {
         List<Integer> excludePorts = new ArrayList<>();
         excludePorts.add(8083);
         return new CustomRule(excludePorts);
     }
}
