package com.ithawk.demo.rocketmq.springcloudalibaba.consume;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {


    @Bean
    public ConsumerCustomRunner customRunner() {
        return new ConsumerCustomRunner();
    }

}
