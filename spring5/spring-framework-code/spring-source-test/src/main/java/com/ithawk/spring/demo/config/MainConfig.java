package com.ithawk.spring.demo.config;


import com.ithawk.spring.demo.bean.Cat;
import com.ithawk.spring.demo.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;


/**
 * 这是一个配置类
 */

//@Import({ Person.class,MainConfig.MyImportRegistrar.class}) // 2
//@Import(AspectJAutoProxyRegistrar.class)
@ComponentScan("com.ithawk.spring.demo") //3 定义扫描地址 对应的bean 需要加入注解
@Configuration
public class MainConfig {

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	public MainConfig(){
		System.out.println("MainConfig...创建了....");
//		applicationEventPublisher.publishEvent();
	}

	// 1:直接创建对象
//	@Bean(initMethod = "")
//	public Person person(){
//		Person person = new Person();
//		person.setName("李四");
//		return person;
//	}


	// 2
	/**
	 * 		BeanDefinitionRegistry：Bean定义信息注册中心：图纸中心;
	 * 				它里面都是BeanDefinition
	 *
	 * 	<bean class="com.atguigu.spring.bean.Person" id="person">
	 * 		<property name="name" value="张三"/>
	 * 	</bean>
	 * 	 对应
	 * 	RootBeanDefinition
	 *
	 */
	static class MyImportRegistrar implements ImportBeanDefinitionRegistrar{
		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
											BeanDefinitionRegistry registry) {


//			Enhancer enhancer = new Enhancer();
//			enhancer.setCallbacks(new MethodInterceptor(){
//
//				@Override
//				public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//					return null;
//				}
//			});
//			enhancer.setSuperclass(HelloService.class);
			// BeanDefinition
			RootBeanDefinition catDefinition = new RootBeanDefinition();
			catDefinition.setBeanClass(Cat.class);
//			catDefinition.setInitMethodName("aaa");
			//可以声明定义信息，包括我需要自动装配什么？
//			catDefinition.setInstanceSupplier(()-> new Cat());
			//Spring 这个实例的类型，名字
			registry.registerBeanDefinition("tomCat",catDefinition);
		}
	}
}


