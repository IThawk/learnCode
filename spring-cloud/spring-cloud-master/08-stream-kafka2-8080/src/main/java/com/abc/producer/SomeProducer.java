package com.abc.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
// 将MQ与生产者类通过消息管道相绑定
@EnableBinding({Source.class, CustomSource.class})
public class SomeProducer {
    // 必须使用byName方式的自动注入
    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel channel;

    @Autowired
    @Qualifier(CustomSource.CHANNEL_NAME)
    private MessageChannel customChannel;

    public String sendMessage(String msg) {
        // 将消息写入到两个管道，将会写入到两个主题
        channel.send(MessageBuilder.withPayload(msg).build());
        customChannel.send(MessageBuilder.withPayload(msg).build());
        return msg;
    }
}
