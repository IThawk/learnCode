package com.gupaoedu.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
// @EnableGlobalMethodSecurity(prePostEnabled = true) spring表达式注解
// @EnableGlobalMethodSecurity(jsr250Enabled = true)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class GpVipSecurity1011Application {

	public static void main(String[] args) {
		SpringApplication.run(GpVipSecurity1011Application.class, args);
	}

}
