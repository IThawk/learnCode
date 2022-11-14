package com.ithawk.demo.rabbitmq.springboot.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 *
 */
@SpringBootApplication
@EnableOpenApi //swagger
@MapperScan("com.ithawk.demo.rabbitmq.springboot.producer.mapper")
public class ProducerApp {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApp.class, args);
	}
}
