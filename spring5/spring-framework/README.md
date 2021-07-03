spring web 启动加载流程：
* org.springframework.web.SpringServletContainerInitializer.onStartup
  org.springframework.web.context.ContextLoader.initWebApplicationContext
  org.springframework.web.context.support.XmlWebApplicationContext
  主要逻辑都在 方法里面 refresh
  org.springframework.context.support.AbstractApplicationContext.refresh
  org.springframework.web.context.support.StandardServletEnvironment.initPropertySources
  org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory
  org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory
  org.springframework.context.support.AbstractRefreshableApplicationContext.createBeanFactory
  org.springframework.web.context.support.XmlWebApplicationContext.loadBeanDefinitions(org.springframework.beans.factory.support.DefaultListableBeanFactory)
  org.springframework.beans.factory.xml.XmlBeanDefinitionReader.registerBeanDefinitions
  org.springframework.beans.factory.support.DefaultListableBeanFactory

org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List<org.springframework.beans.factory.config.BeanFactoryPostProcessor>)


获取bean
org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanNamesForAnnotation
 初始化事件模块
org.springframework.context.event.SimpleApplicationEventMulticaster

org.springframework.context.support.AbstractApplicationContext.onRefresh
org.springframework.ui.context.support.UiApplicationContextUtils.initThemeSource
初始化监听器
org.springframework.context.support.AbstractApplicationContext.registerListeners

获取bean
org.springframework.beans.factory.support.AbstractBeanFactory.getBean(java.lang.String)
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(java.lang.String)

创建bean  就已经生成切面类了
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])

org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean


org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance

# org.springframework.beans.BeanUtils.instantiateClass(java.lang.Class<T>)