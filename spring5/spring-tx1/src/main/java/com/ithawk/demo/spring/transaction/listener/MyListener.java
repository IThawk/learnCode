package com.ithawk.demo.spring.transaction.listener;

import com.ithawk.demo.spring.transaction.MailSendEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener{

    @EventListener(condition = "#mailSendEvent.to=='1'")
    public void onApplicationEvent1(MailSendEvent mailSendEvent) throws InterruptedException {
        MailSendEvent event = mailSendEvent;
        Thread.sleep(5000);
        System.out.println("111111 MailSender1向"+ event.getTo()+ "发送了邮件"+System.currentTimeMillis());
    }

    @EventListener(condition = "#mailSendEvent.to=='2'")
    public void onApplicationEvent2(MailSendEvent mailSendEvent) throws InterruptedException {
        Thread.sleep(5000);
        MailSendEvent event = mailSendEvent;
        System.out.println("22222 MailSender2向"+ event.getTo()+ "发送了邮件"+System.currentTimeMillis());
    }
}