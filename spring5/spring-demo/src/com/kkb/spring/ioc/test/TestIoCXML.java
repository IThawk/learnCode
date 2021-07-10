package com.kkb.spring.ioc.test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kkb.spring.ioc.xml.po.ClassA;
import com.kkb.spring.ioc.xml.po.ClassB;
import com.kkb.spring.ioc.xml.po.Course;
import com.kkb.spring.ioc.xml.po.Student;

public class TestIoCXML {
	
	@Test
	public void test11() {
		// 指定XML路径
		String path = "spring/beans.xml";
		Resource resource = new ClassPathResource(path);
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource );
		// Bean实例创建流程
		DataSource dataSource = (DataSource) beanFactory.getBean("dataSource");
		System.out.println(dataSource);
	}

	@Test
	public void test1() {
		// 指定XML路径
		String path = "spring/beans.xml";
		// 创建DefaultListableBeanFactory工厂，这也就是Spring的基本容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 创建BeanDefinition阅读器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		// 进行BeanDefinition注册流程
		reader.loadBeanDefinitions(path);
		// Bean实例创建流程
		DataSource dataSource = (DataSource) beanFactory.getBean("ds");
		System.out.println(dataSource);
	}

	@Test
	public void testInitApplicationContext() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean("student");
		Student bean2 = (Student) context.getBean("classC");
		System.out.println(bean);
		System.out.println(bean2);
	}

	@Test
	public void testInitApplicationContext2() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean(Student.class);
		System.out.println(bean);
	}

	@Test
	public void test() throws Exception {
		Class<?> clazz = Course.class;
		Field field = clazz.getDeclaredField("name");
		System.out.println(field.getType());
		field.setAccessible(true);

		Object object = clazz.newInstance();

		field.set(object, null);
	}

	@Test
	public void test2() throws Exception {
		Course course = new Course();
		// 操作单个属性
		PropertyDescriptor pd = new PropertyDescriptor("name", Course.class);
		Class<?> propertyType = pd.getPropertyType();
		System.out.println(propertyType);
		Method w = pd.getWriteMethod();// 获取属性的setter方法
		w.invoke(course, "winclpt");
		System.out.println(course.getName());
		Method r = pd.getReadMethod();// 获取属性的getter方法
		Object object = r.invoke(course, null);
		System.out.println(object.toString());

		// 操作所有属性
		BeanInfo bi = Introspector.getBeanInfo(Course.class);
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (PropertyDescriptor p : pds) {

		}
	}

	/**
	 * 循环依赖演示
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		ClassA classA = (ClassA) context.getBean(ClassA.class);
		System.out.println(classA.getClass());
		System.out.println(classA.getClassB().getClass());
		ClassB classB = (ClassB) context.getBean(ClassB.class);
		System.out.println(classB.getClass());
		System.out.println(classB.getClassA().getClass());
	}

}
