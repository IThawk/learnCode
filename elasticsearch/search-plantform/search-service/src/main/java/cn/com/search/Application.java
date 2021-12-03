package cn.com.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@ComponentScan(basePackages = {"cn.com.search"})

public class Application {
    public static void main(String[] args) {
    	//availableProcessors is already set to [4], rejecting [4]
    	System.setProperty("es.set.netty.runtime.available.processors", "false");
    	SpringApplication.run(Application.class, args);
    }
}