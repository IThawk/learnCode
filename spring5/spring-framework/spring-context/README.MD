
# spring启动的入口
org.springframework.context.support.ClassPathXmlApplicationContext.ClassPathXmlApplicationContext(java.lang.String[], boolean, org.springframework.context.ApplicationContext)

org.springframework.context.support.AbstractApplicationContext.refresh

org.springframework.context.support.AbstractApplicationContext.prepareBeanFactory

org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory

# 加载bean位置
org.springframework.context.support.AbstractXmlApplicationContext.loadBeanDefinitions(org.springframework.beans.factory.support.DefaultListableBeanFactory)

## 开始加载bean
org.springframework.context.support.AbstractXmlApplicationContext.loadBeanDefinitions(org.springframework.beans.factory.xml.XmlBeanDefinitionReader)

org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(java.lang.String...)

## 执行加载bean
org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions
## 注册bean
org.springframework.beans.factory.xml.XmlBeanDefinitionReader.registerBeanDefinitions
org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader.doRegisterBeanDefinitions

## beanMap的存储位置
org.springframework.beans.factory.support.DefaultListableBeanFactory.beanDefinitionMap
# 注册bean
org.springframework.context.support.AbstractApplicationContext.registerBeanPostProcessors

org.springframework.context.support.AbstractApplicationContext.getBeanNamesForType(java.lang.Class<?>, boolean, boolean)

org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanNamesForType(java.lang.Class<?>, boolean, boolean)



org.springframework.beans.factory.support.DefaultListableBeanFactory