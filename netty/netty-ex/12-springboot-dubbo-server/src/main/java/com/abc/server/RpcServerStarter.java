package com.abc.server;

import com.abc.registry.RegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.abc")
public class RpcServerStarter implements CommandLineRunner {
    @Autowired
    private RpcServer server;
    @Autowired
    private RegistryCenter center;

    public static void main(String[] args) {
        SpringApplication.run(RpcServerStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        server.setCenter(center);
        server.setServiceAddress("127.0.0.1:9999");
        server.publish("com.abc.service");
        server.start();
    }
}
