
package com.ithawk.demo.rocketmq.springcloudalibaba.produce;

import com.ithawk.demo.rocketmq.springcloudalibaba.produce.service.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@EnableBinding({ MySource.class })
@EnableOpenApi //swagger
public class RocketMQProduceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketMQProduceApplication.class, args);
	}


}
