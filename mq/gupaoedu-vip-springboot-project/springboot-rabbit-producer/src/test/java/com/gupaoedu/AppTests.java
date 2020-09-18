package com.gupaoedu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gupaoedu.producer.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 记得一定要先启动消费者，否则交换机和队列以及绑定关系都不会创建
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    RabbitSender rabbitSender;

	@Test
	public void contextLoads() throws JsonProcessingException {
	    // 先启动消费者 consumer，否则交换机、队列、绑定都不存在
        rabbitSender.send();
	}

}
