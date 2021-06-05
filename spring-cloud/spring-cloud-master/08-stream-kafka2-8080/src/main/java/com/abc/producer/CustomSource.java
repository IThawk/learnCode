package com.abc.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义管道
 */
public interface CustomSource {
    String CHANNEL_NAME = "xxx";

    @Output(CustomSource.CHANNEL_NAME)
    MessageChannel output();
}
