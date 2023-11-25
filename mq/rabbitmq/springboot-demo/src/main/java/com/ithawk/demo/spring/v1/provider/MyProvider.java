package com.ithawk.demo.spring.v1.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyProvider {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send() {
        // 发送4条消息

        try {
            for (int i = 0; i < 5; i++) {
                amqpTemplate.convertAndSend("", "FIRST_QUEUE", "-------- a direct msg" + i);

                amqpTemplate.convertAndSend("", "FIRST_QUEUE", "-------- a direct msg" + i);
                amqpTemplate.convertAndSend("TOPIC_EXCHANGE", "shanghai.gupao.teacher", "-------- a topic msg : shanghai.gupao.teacher");
                amqpTemplate.convertAndSend("TOPIC_EXCHANGE", "changsha.gupao.student", "-------- a topic msg : changsha.gupao.student");

                amqpTemplate.convertAndSend("FANOUT_EXCHANGE", "", "-------- a fanout msg");
            }
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
