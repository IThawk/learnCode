# dubbo -spring xml 形式的使用
## 服务提供者 dubbo-demo-xml-provider

### maven 依赖
```xml
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
            <version>${dubbo.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>${curator.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>${curator.version}</version>
        </dependency>
```
### 配置文件属性：
```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">

    <dubbo:application metadata-type="remote" name="demo-provider"/>
    <dubbo:metadata-report address="zookeeper://127.0.0.1:12181"/>

    <dubbo:registry address="zookeeper://127.0.0.1:12181"/>
    <!-- dubbo 发布端口 20880 -->
    <dubbo:protocol name="dubbo" port="20880"/>

    <bean id="demoService" class="org.apache.dubbo.demo.provider.DemoServiceImpl"/>

    <!-- dubbo 发布服务 -->
    <dubbo:service interface="org.apache.dubbo.demo.DemoService" ref="demoService"/>

</beans>
```

### 启动类
```java
public class Application {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}
```
### 服务实现
```java
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        System.out.println("sayHello");
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    // 异步调用
    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return "async result";
        });
        return cf;
    }
}

```
## dubbo服务消费方
## maven依赖
```xml
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
            <version>${dubbo.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>${curator.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>${curator.version}</version>
        </dependency>
```
### 服务消费配置
```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">

    <dubbo:application name="demo-consumer"/>

    <dubbo:registry address="zookeeper://127.0.0.1:12181"/>

<!--    Failover Cluster-->
<!--    失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 retries="2" 来设置重试次数(不含第一次)。-->
<!--    Failfast Cluster-->
<!--    快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录。-->

<!--    Failsafe Cluster-->
<!--    失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。-->

<!--    Failback Cluster-->
<!--    失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。-->

<!--    Forking Cluster-->
<!--    并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数。-->

<!--    Broadcast Cluster-->
<!--    广播调用所有提供者，逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息。-->
<!--    timeout="500" 超时时间是毫秒-->
    <dubbo:reference id="demoService" check="false" cluster="failfast" retries="4"
                     timeout="500" interface="org.apache.dubbo.demo.DemoService"
                     mock="org.apache.dubbo.demo.consumer.DemoServiceImplMock"
    />

</beans>
```

### 启动类
```java
public class Application {
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);

        CompletableFuture<String> hello = demoService.sayHelloAsync("world");
        System.out.println("sayHelloAsync result: " + hello.get());

        String hello1 = demoService.sayHello("world");
        System.out.println("sayHello result: " + hello1);
    }
}
```
### mock 类
```java
public class DemoServiceImplMock implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImplMock.class);

    @Override
    public String sayHello(String name) {
        System.out.println("DemoServiceImplMock");
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "DemoServiceImplMock Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return "DemoServiceImplMock async result";
        });
        return cf;
    }
}

```