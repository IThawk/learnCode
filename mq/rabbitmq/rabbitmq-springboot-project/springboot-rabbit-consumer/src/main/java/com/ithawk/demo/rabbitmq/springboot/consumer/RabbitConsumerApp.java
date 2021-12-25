package com.ithawk.demo.rabbitmq.springboot.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 启动程序，会创建对象，开始监听
 */
@SpringBootApplication
@EnableOpenApi //swagger
public class RabbitConsumerApp {
	public static void main(String[] args) {
		SpringApplication.run(RabbitConsumerApp.class, args);
	}
}
