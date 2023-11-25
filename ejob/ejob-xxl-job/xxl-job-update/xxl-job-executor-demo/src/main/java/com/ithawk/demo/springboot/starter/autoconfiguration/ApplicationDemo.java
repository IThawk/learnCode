package com.ithawk.demo.springboot.starter.autoconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableXxlJobExecutorAutoConfiguration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ApplicationDemo {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemo.class, args);
    }

}
