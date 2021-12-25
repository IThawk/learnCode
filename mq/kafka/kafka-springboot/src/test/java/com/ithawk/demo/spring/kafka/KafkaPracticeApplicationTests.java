package com.ithawk.demo.spring.kafka;

import com.ithawk.demo.spring.kafka.kafkapractice.TestKafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaPracticeApplicationTests {

    @Autowired
    TestKafkaProducer testKafkaProducer;
    @Test
    public void contextLoads() {
        testKafkaProducer.send();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
