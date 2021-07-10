spring web 启动加载流程：
```java
/**
 * @see org.springframework.web.SpringServletContainerInitializer.onStartup
 * @see org.springframework.web.context.ContextLoader.initWebApplicationContext
 * @see org.springframework.web.context.support.XmlWebApplicationContext
 * 主要的逻辑都在：
 * @see org.springframework.context.support.AbstractApplicationContext.refresh
 * 
 * 
*/
```
/** org.springframework.web.SpringServletContainerInitializer.onStartup
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
### 真正加载bean的位置
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
// 完成Bean实例的创建（实例化、填充属性、初始化）
#### 循环依赖进入
    org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean


org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)

创建AOP代理
org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy
获取对应的动态代理工厂
org.springframework.aop.framework.DefaultAopProxyFactory.createAopProxy
org.springframework.aop.framework.DefaultAdvisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice
# org.springframework.beans.BeanUtils.instantiateClass(java.lang.Class<T>)

# org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver
* 设置Handler配置文件读取的位置
cglib:
  底层使用：ASM API使用：
  ClassWriter classWriter = new ClassWriter(0);
  classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null,
  "java/lang/Object", null);
  MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "
  <init>",
  "()V", null, null);
  initVisitor.visitCode();//访问开始
  initVisitor.visitVarInsn(Opcodes.ALOAD, 0);//this指针入栈
  initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "
  <init>",
  "()V");//调用构造函数
  initVisitor.visitInsn(Opcodes.RETURN);
  initVisitor.visitMaxs(1, 1);//设置栈长、本地变量数



由于JDK代理是基于接口的，而接口里面又不允许有静态方法，所以是无法代理静态方法的。
换个角度：基于接口的Jdk代理与基于继承Class的代理本质都是基于继承之后重写指定方法实现的代理，
而static方法是属于class的，而不是类实例的，无法被重写所以static方法无法代理。
除此之外，JDK代理类是基于接口实现生成的，因此对于子类的final方法是可以代理的。


# SPRING MVC
org.springframework.web.servlet.DispatcherServlet