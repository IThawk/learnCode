package com.ithawk.demo.netty.springboot.dubbo.consumer;


import com.ithawk.demo.netty.springboot.dubbo.client.RpcProxy;
import com.ithawk.demo.netty.springboot.dubbo.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ithawk.demo.netty.springboot.dubbo")
public class RpcConsumer implements CommandLineRunner {
    @Autowired
    private RpcProxy proxy;

    public static void main(String[] args) {
        SpringApplication.run(RpcConsumer.class, args);
    }

    @Override
    public void run(String... args) {
        SomeService service = proxy.create(SomeService.class, "Alipay");
        System.out.println(service.hello("tom"));
        System.out.println(service.hashCode());
    }
}
