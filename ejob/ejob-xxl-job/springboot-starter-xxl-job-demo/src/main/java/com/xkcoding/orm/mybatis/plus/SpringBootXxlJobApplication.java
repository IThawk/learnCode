package com.xkcoding.orm.mybatis.plus;

import com.ithawk.demo.springboot.starter.autoconfiguration.EnableXxlJobAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.ReactiveTransactionManager;
/**
 * <p>
 * 启动器
 * </p>
 */
@EnableXxlJobAutoConfiguration()
@SpringBootApplication
public class SpringBootXxlJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootXxlJobApplication.class, args);
    }
}
