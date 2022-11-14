# spring-framework-custom-18 首先spring代码

## BeanFacotry

    BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。
    原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。
    ApplicationContext接口,它由BeanFactory接口派生而来，ApplicationContext包含BeanFactory的所有功能，
    通常建议比BeanFactory优先

### BeanFactory和FactoryBean的区别

      BeanFactory是接口，提供了OC容器最基本的形式，给具体的IOC容器的实现提供了规范，
      FactoryBean也是接口，为IOC容器中Bean的实现提供了更加灵活的方式，
      FactoryBean在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式(如果想了解装饰模式参考：
      修饰者模式(装饰者模式，Decoration) 我们可以在getObject()方法中灵活配置。其实在Spring源码中有很多FactoryBean的实现类.

### 区别：

#### BeanFactory是个Factory，也就是IOC容器或对象工厂，

    FactoryBean是个Bean。在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的。
    但对FactoryBean而言，这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,它的实现与设计模式中的工厂模式和修饰器模式类似
    BeanFactory，以Factory结尾，表示它是一个工厂类(接口)， 它负责生产和管理bean的一个工厂。
    在Spring中，BeanFactory是IOC容器的核心接口，它的职责包括：实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。
    BeanFactory只是个接口，并不是IOC容器的具体实现，但是Spring容器给出了很多种实现，如 DefaultListableBeanFactory、XmlBeanFactory、ApplicationContext等，
    其中XmlBeanFactory就是常用的一个，该实现将以XML方式描述组成应用的对象及对象间的依赖关系。XmlBeanFactory类将持有此XML配置元数据，并用它来构建一个完全可配置的系统或应用。   
    都是附加了某种功能的实现。 它为其他具体的IOC容器提供了最基本的规范，
    例如DefaultListableBeanFactory,XmlBeanFactory,ApplicationContext 等具体的容器都是实现了BeanFactory，再在其基础之上附加了其他的功能。
    BeanFactory和ApplicationContext就是spring框架的两个IOC容器，现在一般使用ApplicationnContext，其不但包含了BeanFactory的作用，同时还进行更多的扩展。

#### FactoryBean

    一般情况下，Spring通过反射机制利用<bean>的class属性指定实现类实例化Bean，在某些情况下，实例化Bean过程比较复杂，
    如果按照传统的方式，则需要在<bean>中提供大量的配置信息。配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。
    Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，用户可以通过实现该接口定制实例化Bean的逻辑。
    FactoryBean接口对于Spring框架来说占用重要的地位，Spring自身就提供了70多个FactoryBean的实现。
    它们隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。从Spring3.0开始，FactoryBean开始支持泛型，
    即接口声明改为FactoryBean<T>的形式以Bean结尾，表示它是一个Bean，
    不同于普通Bean的是：它是实现了FactoryBean<T>接口的Bean，
    根据该Bean的ID从BeanFactory中获取的实际上是FactoryBean的getObject()返回的对象，
    而不是FactoryBean本身，如果要获取FactoryBean对象，请在id前面加一个&符号来获取。
    例如自己实现一个FactoryBean，功能：用来代理一个对象，对该对象的所有方法做一个拦截，在调用前后都输出一行LOG，模仿ProxyFactoryBean的功能。

# spring

## 介绍：

### 核心容器：

    核心容器由spring-beans、 spring-core、 spring-context和spring-expression C Spring Expression 
    Language, SpEL) 4个模块组成。
    spring-beans和spring-core模块是Spring框架的核心模块，包含了控制反转CInversion of Control, 
    IOC）和依赖注入（Dependency I时ection, DI〕 。 BeanFactory使用控制反转对应用程序的配置和
    依赖性规范与实际的应用程序代码进行了分离。但BeanFactory实例化后并不会自动实例化Bean,
    只有当Bean被使用时，BeanFactory才会对该Bean 进行实例化与依赖关系的装配。
    spring-context模块构架于核心模块之上，扩展了BeanFactory，为它添加了Bean生命周期控
    制、框架事件体系及资源加载透明化等功能。 此外，该模块还提供了许多企业级支持，如邮件访
    问、远程访问、任务调度等，ApplicationContext是该模块的核心接口，它的超类是BeanFactoryo 
    与BeanFactory不同，ApplicationContext实例化后会自动对所有的单实例Bean进行实例化与依赖
    关系的装配，使之处于待用状态。
    spring-context-support模块是对SpringIoC容器及IoC子容器的扩展支持。
    spring_context-indexer模块是Spring的类管理组件和Classpath扫描组件。
    spring-expression 模块是统一表达式语言（EL) 的扩展模块，可以查询、管理运行中的对象，
    同时也可以方便地调用对象方法， 以及操作数组、集合等。 它的语法类似于传统EL，但提供了额
    外的功能，最出色的要数函数调用和简单字符串的模板函数。EL的特性是基于Spring产品的需求
    而设计的，可以非常方便地同SpringIoC进行交互。   
    BeanFactory底层支持两个对象模型。
    ( 1 ）单例模型：提供了具有特定名称的全局共享实例对象，可以在查询时对其进行检索。
    Singleton是默认的、也是最常用的单例模型。
    (2）原型模型： 确保每次检索都会创建单独的实例对象。在每个用户都需要自己的对象时，
    采用原型模型。
    BeanFactory (Bean工厂）是Spring作为IoC容器的基础。 IoC则将处理事情的责任从应用程
    序代码转移到框架。 

### AOP

    AOP和设备支持由spring-aop, spring-aspects和spring-instrument3个模块组成。
    spring-aop是Spring的另一个核心模块，是AOP主要的实现模块。 作为继OOP后对程序员影
    响最大的编程思想之一，AOP极大地拓展了人们的编程思路。Spring以NM的动态代理技术为基
    础， 设计出了一系列的AOP横切实现，比如前置通知、返回通知、异常通知等。同时，Pointcut
    接口可以匹配切入点，可以使用现有的切入点来设计横切面，也可以扩展相关方法根据需求进行
    切入。
    spring-aspects模块集成自AspectJ框架，主要是为Spring提供多种AOP实现方法。
    spring-instrument模块是基于JavaSE中的java.lang.instrurnent进行设计的，应该算AOP的一
    个支援模块，主要作用是在JVM启用时生成一个代理类，程序员通过代理类在运行时修改类的字
    节，从而改变一个类的功能，实现AOP。
    Spring AOP编写的应用程序代码是松藕合的。
    AOP的功能完全集成到了Spring事务、日志和其他各种特性的上下文中。
    AOP编程的常用场景有： Authentication（权限认i.iE), Auto Caching （自动缓存）、ErrorHandling 
    （错误处理）、 Debugging （调试）、Logging （日志）、Transaction （事务）等。

### 数据集成：

    数据访问与集成由spring才dbc、spring-tx、 spring-orm、 spring-oxm和spring- ns 5个模块组成。
    spring-jdbc模块是Spring 提供的JDBC抽象框架的主要实现模块，用于简化Spring JDBC操
    作。 主要提供JDBC模板方式、关系数据库对象化方式、SimpleJdbc方式、事务管理来简化JDBC
    编程，主要实现类有Jdbc Temp late、 SimpleJdbcTemplate及NamedParameterJdbcTemplate。
    spring-tx模块是Spring JDBC事务控制实现模块。 Spring对事务做了很好的封装，通过它的
    AOP配置，可以灵活地在任何一层配置。 但是在很多需求和应用中，直接使用JDBC事务控制还
    是有优势的。 事务是以业务逻辑为基础的，一个完整的业务应该对应业务层里的一个方法，如果
    业务操作失败，则整个事务回滚，所以事务控制是应该放在业务层的。 持久层的设计则应该遵循
    一个很重要的原则：保证操作的原子性，即持久层里的每个方法都应该是不可分割的。 在使用
    Spring JDBC控制事务时，应该注意其特殊性。
    spring-orm模块是ORM框架支持模块，主要集成Hibernate,Java Persistence API(JPA）和Java
    Data Objects CJDO）用于资源管理、数据访问对象（DAO）的实现和事务策略。
    spring-oxm模块主要提供一个抽象层以支撑OXM(OXM是Object-to-XML-Mapping的缩写，
    它是一个O/M-mapper，将Java对象映射成XML数据，或者将XML数据映射成Java对象），例
    如JAXB、 Castor、 XMLBeans、 JiBX和XStream等。
    spring才ms模块能够发送和接收信息，自Spring 4.1 开始，它还提供了对spring-messaging模
    块的支撑。

### WEB

    Web组件由spring-web、 spring-webmvc、 spring-websocket和spring-webflux4个模块组成。
    spring-web模块为Spring提供了最基础的Web支持，主要建立在核心容器之上， 通过Serviet 
    或者Listeners来初始化IoC容器，也包含一些与Web相关的支持。
    众所周知，spring-webmvc模块是一个Web-Servlet模块， 实现了Spring MVC (Model-View
    Controller）的Web应用。
    spring-websocket模块是与Web前端进行全双工通信的协议。
    spring州ebflux是一个新的非堵塞函数式Reactive Web框架，可以用来建立异步的、非阻塞
    的、事件驱动的服务，并且扩展性非常好。

### 通信

    通信报文即spring-messaging模块，它是Spring4新加入的一个模块，主要职责是为Spring 框
    架集成一些基础的报文传送应用。  

## 项目描述：

### spring-1.0

* 手写spring版本V1

### spring-tx:spring事务

* 包含spring事务，事件监听的使用方法

## java动态代理和cglib动态代理

```
一、原理区别：

java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。

而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP

3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换

如何强制使用CGLIB实现AOP？
（1）添加CGLIB库，SPRING_HOME/cglib/*.jar
（2）在spring配置文件中加入<aop:aspectj-autoproxy proxy-target-class="true"/>

JDK动态代理和CGLIB字节码生成的区别？
（1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
（2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
因为是继承，所以该类或方法最好不要声明成final 
```
