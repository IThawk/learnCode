package com.ithawk.demo.rocketmq.springcloudalibaba.produce.config;

import com.ithawk.demo.rocketmq.springcloudalibaba.produce.service.CustomRunner;
import com.ithawk.demo.rocketmq.springcloudalibaba.produce.service.CustomRunnerWithTransactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public CustomRunner customRunner() {
        return new CustomRunner("output1");
    }

    @Bean
    public CustomRunner customRunner2() {
        return new CustomRunner("output3");
    }

    @Bean
    public CustomRunnerWithTransactional customRunnerWithTransactional() {
        return new CustomRunnerWithTransactional();
    }


}
