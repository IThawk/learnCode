package com.ithawk.demo.ejob.springboot;

import com.ithawk.demo.ejob.springboot.config.EmbedZookeeperServer;
import com.ithawk.demo.ejob.springboot.utils.KillServerUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootElasticJobBootstrap {
	private static final int EMBED_ZOOKEEPER_PORT = 4181;
	public static void main(String[] args) {
		if (KillServerUtils.exPort(EMBED_ZOOKEEPER_PORT)){
			EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
		}

		SpringApplication.run(SpringbootElasticJobBootstrap.class, args);
	}
}
