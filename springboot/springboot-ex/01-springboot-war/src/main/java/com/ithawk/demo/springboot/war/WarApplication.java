package com.ithawk.demo.springboot.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 将war包放在 webapps中
 * http://127.0.0.1:8080/01-springboot-war-1.0-SNAPSHOT/some
 */
@SpringBootApplication
public class WarApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarApplication.class, args);
    }

}
