package com.ithawk.spring.demo;


import com.alibaba.fastjson2.JSON;
import com.ithawk.spring.demo.aop.HelloService;
import com.ithawk.spring.demo.bean.Cat;
import com.ithawk.spring.demo.bean.Hello;
import com.ithawk.spring.demo.bean.Person;
import com.ithawk.spring.demo.bean.PersonD;
import com.ithawk.spring.demo.circle.A;
import com.ithawk.spring.demo.config.MainConfig;
import com.ithawk.spring.demo.listener.AppEventPublisher;
import com.ithawk.spring.demo.listener.ChangeEvent;
import com.ithawk.spring.demo.listener.MessageEvent;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @see  org.springframework.beans.factory.support.AbstractBeanDefinition#AbstractBeanDefinition()
 *
 * 注解版Spring的用法
 * 入口 ：
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext(java.lang.Class[])
 * 1：注册读取器
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext()
 * 注册配置处理器（添加配置处理器）
 * @see AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry)
 * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)
 *
 * 扫描器 准备环境变量
 * @see ClassPathBeanDefinitionScanner#ClassPathBeanDefinitionScanner(BeanDefinitionRegistry, boolean, Environment, ResourceLoader)
 *
 * 2：注册主配置类
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext#register(java.lang.Class[])
 * 创建主配置类
 * @see AnnotatedBeanDefinitionReader#registerBean(Class)
 * @see AnnotatedBeanDefinitionReader#doRegisterBean(Class, String, Class[], Supplier, BeanDefinitionCustomizer[])
 *
 * 3：注册
 * @see AbstractApplicationContext#refresh()
 *
 * 3.1 准备上下文环境
 * @see org.springframework.context.support.AbstractApplicationContext#prepareRefresh()
 *
 * 3.2 第一次创建beanFactory.并且加载bean definition（xml会在这个地方解析bean定义信息） 定义
 *  注解的时候（org.springframework.context.support.GenericApplicationContext.refreshBeanFactory）
 * @see AbstractApplicationContext#obtainFreshBeanFactory()
 *
 * 3.3 预准备工厂信息 添加一下默认后置处理器
 * @see AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
 * 3.4 后置处理bean 工厂
 * @see org.springframework.context.support.AbstractApplicationContext#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 * 3.5
 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)
 * 扫描类的后置处理器
 * @see ConfigurationClassPostProcessor
 * @see PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(Collection, BeanDefinitionRegistry, ApplicationStartup)
 *
 * @see ConfigurationClassPostProcessor#processConfigBeanDefinitions(BeanDefinitionRegistry)
 * @see ConfigurationClassParser#doProcessConfigurationClass(ConfigurationClass, ConfigurationClassParser.SourceClass, Predicate)
 * @see ComponentScanAnnotationParser#parse(AnnotationAttributes, String)
 * 类信息扫描
 * @see ClassPathBeanDefinitionScanner#doScan(String...)
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#findCandidateComponents(java.lang.String)
 * @see ClassPathScanningCandidateComponentProvider#scanCandidateComponents(String)
 *
 * @see ClassPathScanningCandidateComponentProvider#isCandidateComponent(MetadataReader)
 */
public class AnnotationMainTest {

	public static void main(String[] args) {
//		new ClassPathXmlApplicationContext()

		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(MainConfig.class);
		// 1
//		Person person =	applicationContext.getBean(Person.class);
//		System.out.println(person);

//2
		String[] beans = applicationContext.getBeanDefinitionNames();
		System.out.println("beans: \r\n"+Arrays.stream(beans).collect(Collectors.joining("\r\n,")).toString());



//3
				Hello bean = applicationContext.getBean(Hello.class);
//		Hello bea2 = applicationContext.getBean(Hello.class);
		System.out.println(bean == bean); //还是单例


//3:
		//循环引用,原理测试
		//AOP,原理测试
//		HelloService helloService = applicationContext.getBean(HelloService.class);
//
//		//代理对象来调用方法
//		helloService.sayHello("zhangsan");



//		applicationContext.publishEvent(new Object());
//		applicationContext.publishEvent(new ApplicationEvent() {
//			@Override
//			public String toString() {
//				return super.toString();
//			}
//		});

//		//测试事件
//		AppEventPublisher eventPublisher = applicationContext.getBean(AppEventPublisher.class);
//		eventPublisher.publish(new A());
//		eventPublisher.publish(new MessageEvent("hello，你好"));
//		eventPublisher.publish(new ChangeEvent(eventPublisher,"sending..."));


		PersonD bean1 = applicationContext.getBean(PersonD.class);

		System.out.println(JSON.toJSONString(bean1));


//		String[] names = applicationContext.getBeanDefinitionNames();
//		for (String name : names) {
//			System.out.println(name);
//		}


//		// 原型类
//		Cat bean1 = applicationContext.getBean(Cat.class);
//
//		Cat bean2 = applicationContext.getBean(Cat.class);
//
//		System.out.println("1:" + (bean1 == bean2));  //false
//
//		Person bean3= applicationContext.getBean(Person.class);
//
//		Cat cat = bean3.getCat();
//
//		Person bean4 = applicationContext.getBean(Person.class);
//
//		Cat cat1 = bean4.getCat();
//		System.out.println(cat1 == cat);  //true
//		System.out.println(cat1);


//		Person bean = applicationContext.getBean(Person.class);
//
//		ApplicationContext context = bean.getContext();
//
//		System.out.println(context == applicationContext);



	}
}
