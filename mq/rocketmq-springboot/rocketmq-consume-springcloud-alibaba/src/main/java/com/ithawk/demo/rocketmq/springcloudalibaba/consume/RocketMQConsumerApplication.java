package com.ithawk.demo.rocketmq.springcloudalibaba.consume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding({ MySink.class })
public class RocketMQConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketMQConsumerApplication.class, args);
	}

	@Bean
	public ConsumerCustomRunner customRunner() {
		return new ConsumerCustomRunner();
	}


	public static class ConsumerCustomRunner implements CommandLineRunner {

		@Autowired
		private MySink mySink;

		@Override
		public void run(String... args) throws InterruptedException {
			while (true) {
				mySink.input5().poll(m -> {
					String payload = (String) m.getPayload();
					System.out.println("pull msg: " + payload);
				}, new ParameterizedTypeReference<String>() {
				});
				Thread.sleep(2_000);
			}
		}

	}

}
