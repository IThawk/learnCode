package com.kkb.spring.ioc.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kkb.spring.ioc.annotation.po.Student;

public class TestIoCAnno {

	@Test
	public void test() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.kkb.spring.ioc.annotation.po");
		Student student = context.getBean(Student.class);
		System.out.println(student);
		context.close();
	}

}
