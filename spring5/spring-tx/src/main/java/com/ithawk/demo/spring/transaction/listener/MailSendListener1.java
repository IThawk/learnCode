package com.ithawk.demo.spring.transaction.listener;

import com.ithawk.demo.spring.transaction.MailSendEvent1;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MailSendListener1 implements ApplicationListener<MailSendEvent1> {
    @SneakyThrows
    @Override
    public void onApplicationEvent(MailSendEvent1 mailSendEvent) {
        MailSendEvent1 event = mailSendEvent;
        Thread.sleep(5000);
        System.out.println("MailSender1向" + event.getTo() + "发送了邮件"+System.currentTimeMillis());
    }
}