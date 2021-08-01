package com.ithawk.demo.springcloud.zuul;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringCloudApplication
public class CloudApplicationZuul7000 {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplicationZuul7000.class, args);
    }

    // 设置zuul对consumer的负载均衡策略为”随机策略“
    @Bean
    public IRule loadBalanceRule() {
        return new RandomRule();
    }

}
