package com.ithawk.demo.rabbitmq.springboot.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
@MapperScan("com.ithawk.demo.rabbitmq.springboot.v1.mapper")
public class ProducerApp {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApp.class, args);
	}
}
