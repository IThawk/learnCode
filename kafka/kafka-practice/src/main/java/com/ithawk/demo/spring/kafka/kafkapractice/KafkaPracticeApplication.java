package com.ithawk.demo.spring.kafka.kafkapractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaPracticeApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaPracticeApplication.class, args);
        GpKafkaProducer kp = context.getBean(GpKafkaProducer.class);
        for (int i = 0; i < 10; i++) {
            kp.send();
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
