package org.springframework.demo;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.demo.model.User;
import org.springframework.demo.service.UserService;

public class ApplicationContestDemo {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		User user = applicationContext.getBean("user", User.class);
		UserService userService = applicationContext.getBean("userService",UserService.class);
		System.out.println(userService.userTest());
		System.out.println(user.getName());
		System.out.println("hello world");
	}
}
