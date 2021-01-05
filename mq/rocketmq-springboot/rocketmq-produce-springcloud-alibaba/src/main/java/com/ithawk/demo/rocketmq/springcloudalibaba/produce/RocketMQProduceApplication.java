
package com.ithawk.demo.rocketmq.springcloudalibaba.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@EnableBinding({ MySource.class })
public class RocketMQProduceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketMQProduceApplication.class, args);
	}


}
