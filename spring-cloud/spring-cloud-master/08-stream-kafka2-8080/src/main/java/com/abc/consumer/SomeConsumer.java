package com.abc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

import javax.annotation.PostConstruct;

// @Component
// @EnableBinding(Sink.class)
public class SomeConsumer {

    @Autowired
    @Qualifier(Sink.INPUT)
    private SubscribableChannel channel;

    @PostConstruct
    public void printMessage() {
        channel.subscribe(msg -> {
            // MessageHeaders headers = msg.getHeaders();
            System.out.println(new String((byte[])msg.getPayload()));
        });
    }
}
