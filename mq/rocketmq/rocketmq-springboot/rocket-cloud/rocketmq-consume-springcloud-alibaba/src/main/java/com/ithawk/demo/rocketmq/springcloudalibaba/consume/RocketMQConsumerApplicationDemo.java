package com.ithawk.demo.rocketmq.springcloudalibaba.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({ MySink.class })
public class RocketMQConsumerApplicationDemo {

	public static void main(String[] args) {
		SpringApplication.run(RocketMQConsumerApplicationDemo.class, args);
	}


}
