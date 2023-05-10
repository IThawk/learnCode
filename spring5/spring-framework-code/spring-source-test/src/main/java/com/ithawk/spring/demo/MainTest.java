package com.ithawk.spring.demo;

import com.ithawk.spring.demo.bean.Person;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ApplicationContextAwareProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.IOException;


/**
 *
 *
 * @see org.springframework.context.support.AbstractApplicationContext#refresh
 * 1：加载 BeanDefinitions
 * @see org.springframework.context.support.AbstractRefreshableApplicationContext#refreshBeanFactory
 *
 * @see org.springframework.context.support.AbstractXmlApplicationContext#loadBeanDefinitions(org.springframework.beans.factory.support.DefaultListableBeanFactory)
 * @see org.springframework.context.support.AbstractXmlApplicationContext#loadBeanDefinitions(XmlBeanDefinitionReader)
 * @see org.springframework.beans.factory.support.AbstractBeanDefinitionReader#loadBeanDefinitions(String...)
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader#loadBeanDefinitions(EncodedResource)
 * @see XmlBeanDefinitionReader#doLoadBeanDefinitions(InputSource, Resource)
 * @see XmlBeanDefinitionReader#registerBeanDefinitions(Document, Resource)
 * @see org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions(Element)
 * //读取xml定义信息
 * @see org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader#processBeanDefinition(Element, BeanDefinitionParserDelegate)
 * @see org.springframework.beans.factory.support.BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)
 * @see DefaultListableBeanFactory#registerBeanDefinition(String, BeanDefinition)
 * 2：创建bean
 *
 *
 *
 *
 * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
 * @see DefaultListableBeanFactory#preInstantiateSingletons()
 * @see AbstractBeanFactory#getBean(String)
 *
 * @see AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)
 * @see AbstractAutowireCapableBeanFactory#createBean(String, RootBeanDefinition, Object[])
 * @see AbstractAutowireCapableBeanFactory#doCreateBean(String, RootBeanDefinition, Object[])
 * @see DefaultSingletonBeanRegistry#getSingleton(String)
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBeanInstance(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 * @see org.springframework.beans.factory.support.SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
 * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.CglibSubclassCreator#instantiate(java.lang.reflect.Constructor, java.lang.Object...)
 *
* 3:属性的自动装配 createBeanInstance 执行之后
 * @see AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)
 * 自动装配的属性
 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
 *
 * 4：属性赋值操作 populateBean 执行之后
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
 * BeanPostProcessor 处理器
 * 添加属性前置处理器
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
 * 添加实现aware
 * @see ApplicationContextAwareProcessor#postProcessBeforeInitialization(Object, String)
 *
 *
 * 5：解决循环依赖
 * @see org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)
 *





 */
public class MainTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
		Person bean = context.getBean(Person.class);
		System.out.println(bean);
	}

	public static void test01(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Person bean = context.getBean(Person.class);
		System.out.println(bean);

	}
}
