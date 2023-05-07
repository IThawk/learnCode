package com.ithawk.spring.web.demo.service;


import org.springframework.stereotype.Service;

@Service
public class HelloService {

	public HelloService(){
		System.out.println("HelloService.....");
	}

	public String say(String name){
		return "Hello,"+name;
	}
}
