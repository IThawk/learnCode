package com.ithawk.demo.elasticsearch.logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by on 2018/12/12.
 * @author hulk
 */
@SpringBootApplication
public class LogstashApplication {

    private final  static Logger logger = LoggerFactory.getLogger(LogstashApplication.class);

    public static void main(String[] args) {
        //入口函数
        SpringApplication.run(LogstashApplication.class,args);
    }
}
