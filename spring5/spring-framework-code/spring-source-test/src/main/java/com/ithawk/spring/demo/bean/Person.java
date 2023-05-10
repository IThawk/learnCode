package com.ithawk.spring.demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


/**
 * Aware接口；帮我们装配Spring底层的一些组件
 * 1、Bean的功能增强全部都是有 BeanPostProcessor+InitializingBean  （合起来完成的）
 * 2、骚操作就是 BeanPostProcessor+InitializingBean
 *
 * 你猜Autowired是怎么完成的
 * Person为什么能把ApplicationContext、MessageSource当为自己的参数传进来。
 * 	  - 通过实现接口的方式自动注入了 ApplicationContext、MessageSource。是由BeanPostProcessor（Bean的后置处理器完成的）
 *
 */
@Component
public class Person implements ApplicationContextAware /*2 使用这种*/, MessageSourceAware/*3：国际化*/ {

//	@Autowired //这种方法也可以
	ApplicationContext context;  //可以要到ioc容器

	MessageSource messageSource;




	public Person(){
		System.out.println("person创建....");
	}
//
//	public ApplicationContext getContext() {
//		return context;
//	}


	private String name;

	@Autowired  //依赖的组件是多实例就不能Autowired
	private Cat cat;


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}



//	@Autowired  //去发现一下.....
	public void setCat(Cat cat) {
		this.cat = cat;
	}



	//
//	@Lookup  //去容器中找。  @Bean的这种方式注册的Person @Lookup不生效
	public Cat getCat() {
		return cat;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				'}';
	}


	public ApplicationContext getContext() {
		return context;
	}

	//2：第二种方法添加： applicationContext
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//利用回调机制，把ioc容器传入
		System.out.println("person 执行 aware");
		this.context = applicationContext;
	}

	//3：messageSource 国际化
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
