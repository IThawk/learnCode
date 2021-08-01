package com.abc.codeconfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DepartCodeConfig {

    @LoadBalanced   // 负载均衡方式进行消费
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
