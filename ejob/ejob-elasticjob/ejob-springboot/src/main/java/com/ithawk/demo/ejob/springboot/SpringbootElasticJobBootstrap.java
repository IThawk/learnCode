package com.ithawk.demo.ejob.springboot;

import com.ithawk.demo.ejob.springboot.config.EmbedZookeeperServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootElasticJobBootstrap {
	private static final int EMBED_ZOOKEEPER_PORT = 4181;
	public static void main(String[] args) {

		EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
		SpringApplication.run(SpringbootElasticJobBootstrap.class, args);
	}
}
