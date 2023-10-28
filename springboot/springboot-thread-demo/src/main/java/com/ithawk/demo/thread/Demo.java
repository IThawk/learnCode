package com.ithawk.demo.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ithawk
 * @projectName javaBase
 * @description: CommonTimingTask  里面进行 设置信息  scheduledTaskRegistrar 进行多线程设置
 * @date 2022/1/2015:17
 */
@SpringBootApplication
public class Demo {


    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext= SpringApplication.run(Demo.class, args);
    }
}
