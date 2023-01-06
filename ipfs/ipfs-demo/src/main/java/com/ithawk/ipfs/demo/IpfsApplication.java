package com.ithawk.ipfs.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.ReactiveTransactionManager;
//start 
@SpringBootApplication
public class IpfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpfsApplication.class, args);
	}

}

