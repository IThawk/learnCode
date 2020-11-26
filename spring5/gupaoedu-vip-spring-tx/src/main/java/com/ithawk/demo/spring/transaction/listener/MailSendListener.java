package com.ithawk.demo.spring.transaction.listener;

import com.ithawk.demo.spring.transaction.MailSendEvent;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MailSendListener implements ApplicationListener<MailSendEvent> {
    @SneakyThrows
    @Override
    public void onApplicationEvent(MailSendEvent mailSendEvent) {
        MailSendEvent event = mailSendEvent;
        Thread.sleep(5000);
        System.out.println("MailSender向"+ event.getTo()+ "发送了邮件"+System.currentTimeMillis());
    }
}