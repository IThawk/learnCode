package com.ithawk.demo.rabbitmq.springboot.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ithawk.demo.rabbitmq.springboot.producer.entity.Merchant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@PropertySource("classpath:mq.properties")
public class RabbitSender {

    @Value("${com.ithawk.directexchange}")
    private String directExchange;

    @Value("${com.ithawk.topicexchange}")
    private String topicExchange;

    @Value("${com.ithawk.fanoutexchange}")
    private String fanoutExchange;

    @Value("${com.ithawk.directroutingkey}")
    private String directRoutingKey;

    @Value("${com.ithawk.topicroutingkey1}")
    private String topicRoutingKey1;

    @Value("${com.ithawk.topicroutingkey2}")
    private String topicRoutingKey2;


    // 自定义的模板，所有的消息都会转换成JSON发送
    @Autowired
    AmqpTemplate gupaoTemplate;

    public void send() throws JsonProcessingException {
        Merchant merchant =  new Merchant(1001,"a direct msg : 中原镖局","汉中省解放路266号");
        gupaoTemplate.convertAndSend(directExchange,directRoutingKey, merchant);

        gupaoTemplate.convertAndSend(topicExchange,topicRoutingKey1, "a topic msg : shanghai.ithawk.teacher");
        gupaoTemplate.convertAndSend(topicExchange,topicRoutingKey2, "a topic msg : changsha.ithawk.student");

        // 发送JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(merchant);
        System.out.println(json);
        gupaoTemplate.convertAndSend(fanoutExchange,"", json);
    }


}
