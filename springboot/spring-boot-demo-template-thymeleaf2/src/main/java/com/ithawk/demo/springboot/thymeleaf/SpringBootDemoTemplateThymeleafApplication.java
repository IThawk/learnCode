package com.ithawk.demo.springboot.thymeleaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>

 */
@SpringBootApplication
@MapperScan(basePackages = {"com.ithawk.demo.springboot.thymeleaf.mapper"})
public class SpringBootDemoTemplateThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoTemplateThymeleafApplication.class, args);
	}
}
