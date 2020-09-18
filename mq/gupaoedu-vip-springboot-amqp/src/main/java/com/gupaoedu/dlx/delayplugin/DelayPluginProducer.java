package com.gupaoedu.dlx.delayplugin;

import com.gupaoedu.dlx.ttl.DlxSender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 延时消息插件，去管控台队列看有无收到消息
 * 不能在本地测试，必须发送消息到安装了插件的服务端
 */
@ComponentScan(basePackages = "com.gupaoedu.dlx.delayplugin")
public class DelayPluginProducer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DelayPluginProducer.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        // 延时投递，比如延时4秒
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, +4);
        Date delayTime = calendar.getTime();

        // 定时投递，把这个值替换delayTime即可
        // Date exactDealyTime = new Date("2019/06/24,22:30:00");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "延时插件测试消息，发送时间：" + sf.format(now) + "，理论路由时间：" + sf.format(delayTime);

        MessageProperties messageProperties = new MessageProperties();
        // 延迟的间隔时间，目标时刻减去当前时刻
        messageProperties.setHeader("x-delay", delayTime.getTime() - now.getTime());
        Message message = new Message(msg.getBytes(), messageProperties);

        // 不能在本地测试，必须发送消息到安装了插件的服务端
        rabbitTemplate.send("GP_DELAY_EXCHANGE", "#", message);

    }
}
