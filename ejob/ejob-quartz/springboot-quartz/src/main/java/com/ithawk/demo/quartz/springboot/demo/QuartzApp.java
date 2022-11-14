package com.ithawk.demo.quartz.springboot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ithawk.demo.quartz.springboot.demo.mapper")
public class QuartzApp {

	public static void main(String[] args) {
		SpringApplication.run(QuartzApp.class, args);
	}
}
