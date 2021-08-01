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
 
自动注入
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor

#### 循环依赖进入
    org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean


org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
注意：构造函数的循环依赖可以通过 @Lazy解决
org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency

/** 一级缓存 */
private final Map<String, Object> singletonObjects = new
ConcurrentHashMap<String, Object>(256);
/** 三级缓存 */
private final Map<String, ObjectFactory<?>> singletonFactories = new
HashMap<String, ObjectFactory<?>>(16);
/** 二级缓存 */
private final Map<String, Object> earlySingletonObjects = new
HashMap<String, Object>(16);
这三级缓存分别指：
singletonFactories ： 单例对象工厂的cache
earlySingletonObjects ：提前暴光的单例对象的Cache 。【用于检测循环引用，与
singletonFactories互斥】
singletonObjects：单例对象的cache
我们在创建bean的时候，首先想到的是从cache中获取这个单例的bean，这个缓存就是
singletonObjects。
上面的代码需要解释两个参数：
isSingletonCurrentlyInCreation()判断当前单例bean是否正在创建中，也就是没有初始化完
成(比如A的构造器依赖了B对象所以得先去创建B对象， 或则在A的populateBean过程中依赖了B
对象，得先去创建B对象，这时的A就是处于创建中的状态。)
allowEarlyReference 是否允许从singletonFactories中通过getObject拿到对象
分析getSingleton()的整个过程，Spring首先从一级缓存singletonObjects中获取。如果获取不
到，并且对象正在创建中，就再从二级缓存earlySingletonObjects中获取。如果还是获取不到且允
许singletonFactories通过getObject()获取，就从三级缓存singletonFactory.getObject()(三
级缓存)获取，如果获取到了则：
从singletonFactories中移除，并放入earlySingletonObjects中。其实也就是从三级缓存移动到
了二级缓存。
从上面三级缓存的分析，我们可以知道，Spring解决循环依赖的诀窍就在于singletonFactories这个
三级cache。这个cache的类型是ObjectFactory，
单例对象此时已
经被创建出来(调用了构造器)。这个对象已经被生产出来了，虽然还不完美（还没有进行初始化的第二
步和第三步），但是已经能被人认出来了（根据对象引用能定位到堆中的对象），所以Spring此时将这
个对象提前曝光出来让大家认识，让大家使用。
这样做有什么好处呢？让我们来分析一下“A的某个field或者setter依赖了B的实例对象，同时B的某
个field或者setter依赖了A的实例对象”这种循环依赖的情况。A首先完成了初始化的第一步，并且将
自己提前曝光到singletonFactories中，此时进行初始化的第二步，发现自己依赖对象B，此时就尝
试去get(B)，发现B还没有被create，所以走create流程，B在初始化第一步的时候发现自己依赖了对
象A，于是尝试get(A)，尝试一级缓存singletonObjects(肯定没有，因为A还没初始化完全)，尝试二
级缓存earlySingletonObjects（也没有），尝试三级缓存singletonFactories，由于A通过
ObjectFactory将自己提前曝光了，所以B能够通过ObjectFactory.getObject拿到A对象(虽然A还没
有初始化完全，但是总比没有好呀)，B拿到A对象后顺利完成了初始化阶段1、2、3，完全初始化之后将
自己放入到一级缓存singletonObjects中。此时返回A中，A此时能拿到B的对象顺利完成自己的初始
化阶段2、3，最终A也完成了初始化，进去了一级缓存singletonObjects中，而且更加幸运的是，由
于B拿到了A的对象引用，所以B现在hold住的A对象完成了初始化。
知道了这个原理时候，肯定就知道为啥Spring不能解决“A的构造方法中依赖了B的实例对象，同时B的
构造方法中依赖了A的实例对象”这类问题了！因为加入singletonFactories三级缓存的前提是执行
了构造器，所以构造器的循环依赖没法解决
// 解决循环依赖的关键步骤
boolean earlySingletonExposure =
(mbd.isSingleton()
&& this.allowCircularReferences
&& isSingletonCurrentlyInCreation(beanName));
// 如果需要提前暴露单例Bean，则将该Bean放入三级缓存中
if (earlySingletonExposure) {
// ...
// 将刚创建的bean放入三级缓存中singleFactories(key是beanName，value是
FactoryBean)
addSingletonFactory(beanName,
() -> getEarlyBeanReference(beanName, mbd,
bean));
}
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

# 事务
PlatformTransactionManager
### 事务的传播行为常量（不用设置，使用默认值）
* 先解释什么是事务的传播行为：解决的是业务层之间的方法调用！！
* PROPAGATION_REQUIRED（默认值） -- A中有事务,使用A中的事务.如果没有，B就
  会开启一个新的事务,将A包含进来.(保证A,B在同一个事务中)，默认值！！
* PROPAGATION_SUPPORTS -- A中有事务,使用A中的事务.如果A中没有事
  务.那么B也不使用事务.
* PROPAGATION_MANDATORY -- A中有事务,使用A中的事务.如果A没有事
  务.抛出异常.
* PROPAGATION_REQUIRES_NEW -- A中有事务,将A中的事务挂起.B创建一个新
  的事务.(保证A,B没有在一个事务中)
* PROPAGATION_NOT_SUPPORTED -- A中有事务,将A中的事务挂起.
* PROPAGATION_NEVER -- A中有事务,抛出异常.
* PROPAGATION_NESTED -- 嵌套事务.当A执行之后,就会在这个位置设
  置一个保存点.如果B没有问题.执行通过.如果B出现异常,运行客户根据需求回滚(选择回滚到保
  存点或者是最初始状态)
# SPRING MVC
org.springframework.web.servlet.DispatcherServlet