package com.ithawk.spring.demo;

import com.ithawk.spring.demo.bean.Person;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


/**
 *
 * spring 容器加载入库
 * @see org.springframework.context.support.AbstractApplicationContext#refresh
 * 1：加载 BeanDefinitions
 * @see AbstractApplicationContext#obtainFreshBeanFactory()
 * @see org.springframework.context.support.AbstractRefreshableApplicationContext#refreshBeanFactory
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
 * <p>
 * 2：增强bean factory
 * 2.1：执行BeanDefinitionRegistryPostProcessor的后置处理
 * @see org.springframework.context.support.AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 * 进行工厂后置处理
 * @see PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, List)
 * @see org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(java.util.Collection, org.springframework.beans.factory.support.BeanDefinitionRegistry, org.springframework.core.metrics.ApplicationStartup)
 * @see BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * <p>
 * <p>
 * 2.2 执行 BeanFactoryPostProcessor 后置处理 再 invokeBeanFactoryPostProcessors里面
 * @see PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(Collection, ConfigurableListableBeanFactory)
 * @see BeanFactoryPostProcessor#postProcessBeanFactory(ConfigurableListableBeanFactory)
 * <p>
 * 3: refresh 中注册 bean 处理器 beanPostProcessors 后续会在 registerBeanPostProcessors
 * @see org.springframework.context.support.AbstractApplicationContext#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 * @see PostProcessorRegistrationDelegate#registerBeanPostProcessors(ConfigurableListableBeanFactory, AbstractApplicationContext)
 * 4：registerListeners 注册监听器
 * @see org.springframework.context.support.AbstractApplicationContext#registerListeners()
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForType(java.lang.Class, boolean, boolean)
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doGetBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)
 * @see AbstractBeanFactory#isTypeMatch(String, ResolvableType, boolean)
 * 4：创建bean
 * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
 * @see DefaultListableBeanFactory#preInstantiateSingletons()
 * @see AbstractBeanFactory#getBean(String)
 * @see AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)
 * @see AbstractAutowireCapableBeanFactory#createBean(String, RootBeanDefinition, Object[])
 * @see AbstractAutowireCapableBeanFactory#doCreateBean(String, RootBeanDefinition, Object[])
 * @see DefaultSingletonBeanRegistry#getSingleton(String)
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBeanInstance(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 * @see org.springframework.beans.factory.support.SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
 * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.CglibSubclassCreator#instantiate(java.lang.reflect.Constructor, java.lang.Object...)
 * <p>
 * 5:属性的自动装配 createBeanInstance 执行之后
 * @see AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)
 * 自动装配的属性
 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
 * <p>
 * 6：属性赋值操作 populateBean 执行之后
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
 * 6.1: BeanPostProcessor 处理器
 * 添加属性前置处理器
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
 * 6.1.2 添加实现aware  org.springframework.beans.factory.Aware
 * @see ApplicationContextAwareProcessor#postProcessBeforeInitialization(Object, String)
 * @see ApplicationContextAwareProcessor#invokeAwareInterfaces(Object)
 * 6.2 执行初始化方法 实现 InitializingBean接口
 * @see AbstractAutowireCapableBeanFactory#invokeInitMethods(String, Object, RootBeanDefinition)
 * @see InitializingBean#afterPropertiesSet()
 * 6.3 执行BeanPostProcessor 处理器的后置方法
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization(java.lang.Object, java.lang.String)
 * <p>
 * <p>
 * 6：解决循环依赖
 * @see org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)
 */
public class MainTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Person bean = context.getBean(Person.class);
		System.out.println(bean);
	}

	public static void test01(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Person bean = context.getBean(Person.class);
		System.out.println(bean);

	}
}
