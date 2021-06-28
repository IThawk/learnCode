## Spring Cloud组件
### Eureka
#### CAP原理
    CAP定理指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可兼得。
*  一致性（C）：分布式系统中多个主机之间是否能够保持数据一致的特性。即，当系统数据发生更新操作后，各个主机中的数据仍然处于一致的状态。
* 可用性（A）：系统提供的服务必须一直处于可用的状态，即对于用户的每一个请求，系统总是可以在有限的时间内对用户做出响应。
*  分区容错性（P）：分布式系统在遇到任何网络分区故障时，仍能够保证对外提供满足一致性和可用性的服务。
#### 代码  00-eurekaserver-8000
##### 依赖
```xml
    <properties>
    <java.version>1.8</java.version>
    <spring-cloud.version>Hoxton.SR11</spring-cloud.version>
    <spring-boot-cloud.version>2.2.8.RELEASE</spring-boot-cloud.version>
    <spring-boot-revision>2.3.2.RELEASE</spring-boot-revision>
 </properties>

    <dependencies>

        <!--若你使用的是JDK6、7、8，那么这些依赖无需导入。而JDK9及其以上版本需要导入。
            JAXB,Java Architecture for XML Binding,XML绑定的Java技术。其可以根据
            XML Schema生成Java类。
        -->
        <!--<dependency>-->
            <!--<groupId>javax.xml.bind</groupId>-->
            <!--<artifactId>jaxb-api</artifactId>-->
            <!--<version>2.2.11</version>-->
        <!--</dependency>-->
        <!--<dependency>-->
            <!--<groupId>com.sun.xml.bind</groupId>-->
            <!--<artifactId>jaxb-core</artifactId>-->
            <!--<version>2.2.11</version>-->
        <!--</dependency>-->
        <!--<dependency>-->
            <!--<groupId>com.sun.xml.bind</groupId>-->
            <!--<artifactId>jaxb-impl</artifactId>-->
            <!--<version>2.2.11</version>-->
        <!--</dependency>-->
        <!--<dependency>-->
            <!--<groupId>javax.activation</groupId>-->
            <!--<artifactId>activation</artifactId>-->
            <!--<version>1.1.1</version>-->
        <!--</dependency>-->


        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <version>${spring-boot-revision}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot-revision}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${spring-boot-revision}</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.6.2</version>
        </dependency>

    </dependencies>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
##### yml配置
```yaml
server:
  port: 8000 #eureka端口

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false  # 指定当前主机是否需要向Eureka Server注册自己
    fetch-registry: false   # 指定当前主机是否需要从Eureka Server下载服务注册表
    service-url:
      # 当前Eureka Server对外暴露的服务地址
#      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
      defaultZone: http://localhost:8000/eureka

  server:
    # 关闭自我保护机制
    enable-self-preservation: false
  # 指定自我保护机制的开启阈值
    # renewal-percent-threshold: 0.75
    # 设置server端剔除不可用服务的时间窗，单位毫秒
    eviction-interval-timer-in-ms: 4000
```
#### 查看管理面
    http://127.0.0.1:8000/

#### 集群版本
#### 配置
```yaml
server:
  port: 8100

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka8100.com:8100/eureka,http://eureka8200.com:8200/eureka,http://eureka8300.com:8300/eureka
  instance:
    hostname: eureka8100.com
```

### 原理、代码解析
#### 代码分析入口
#####    @EnableEurekaServer   // 开启Eureka服务
* 找到jar包：
* META-INF/spring.factories: 
  * org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration
    * org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration.eurekaServerBootstrap
    * org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration.peerAwareInstanceRegistry
    * 服务注册、状态修改等  
      * com.netflix.eureka.registry.AbstractInstanceRegistry.register  
      * com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers  
      * com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateInstanceActionsToPeers
      * com.netflix.eureka.cluster.PeerEurekaNode.statusUpdate(java.lang.String, java.lang.String, com.netflix.appinfo.InstanceInfo.InstanceStatus, com.netflix.appinfo.InstanceInfo) 
      * com.netflix.discovery.shared.transport.jersey.AbstractJerseyEurekaHttpClient.statusUpdate  
    * org.springframework.cloud.netflix.eureka.server.InstanceRegistry
  * com.netflix.eureka.resources.InstanceResource （对服务操作的类：注册，下架等）
#####    @EnableEurekaClient  // 注册中心可以是任意的类型
* 找到jar 包；
* META-INF/spring.factories:
  * org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration  
  * org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration.EurekaClientConfiguration
  * org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration.EurekaClientConfiguration.eurekaClient
  * com.netflix.discovery.DiscoveryClient.DiscoveryClient(com.netflix.appinfo.ApplicationInfoManager, com.netflix.discovery.EurekaClientConfig, com.netflix.discovery.AbstractDiscoveryClientOptionalArgs)
  * com.netflix.discovery.DiscoveryClient.DiscoveryClient(com.netflix.appinfo.ApplicationInfoManager, com.netflix.discovery.EurekaClientConfig, com.netflix.discovery.AbstractDiscoveryClientOptionalArgs, javax.inject.Provider<com.netflix.discovery.BackupRegistry>, com.netflix.discovery.shared.resolver.EndpointRandomizer)
  * 启动定时任务： com.netflix.discovery.DiscoveryClient.initScheduledTasks
#### 其他：
#####  @Inject
* 1、@Inject是JSR330 (Dependency Injection for Java)中的规范，需要导入javax.inject.Inject;实现注入。
* 2、@Inject是根据类型进行自动装配的，如果需要按名称进行装配，则需要配合@Named；
* 3、@Inject可以作用在变量、setter方法、构造函数上。


## zuul网关
### 代码 cloud-zuul-7000
#### maven依赖
```xml
<properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Hoxton.SR11</spring-cloud.version>
        <spring-boot-cloud.version>2.2.8.RELEASE</spring-boot-cloud.version>
        <spring-boot-revision>2.3.2.RELEASE</spring-boot-revision>
    </properties>

    <dependencies>
        <!--zuul依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
            <version>${spring-boot-cloud.version}</version>
        </dependency>
        <!--actuator依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <version>${spring-boot-revision}</version>
        </dependency>

        <!--eureka客户端依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <version>${spring-boot-cloud.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot-revision}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${spring-boot-revision}</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.6.2</version>
        </dependency>


        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.11.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.11.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.2</version>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

```
### 配置信息
```yaml
server:
  port: 7000

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8000/eureka

spring:
  application:
    name: zuul-depart

zuul:
  routes:
    # 指定微服务的路由规则
    # *为通配符
    # /** 可以匹配0到多级路径
    # /* 只能匹配一级路径
    # /? 只能匹配一级路径，且路径只能包含一个字符
    abcmsc-consumer-depart-8080: /abc8080/**
    abcmsc-consumer-depart-8090: /abc8090/**
    consumer-depart: /abc1/**
    provider-depart: /abc2/**
  # 屏蔽指定微服务名称
  # ignored-services: abcmsc-consumer-depart-8080
  #屏蔽所有微服务名称
  ignored-services: "*"
  # 指定统一的前辍
  prefix: /abc
  # 屏蔽指定的URI
  ignored-patterns: /**/list/**
  # 屏蔽掉指定的敏感头信息，其会将原来默认的Cookie、SetCookie、Authorization敏感头信息放开
  sensitive-headers: token

```

### 服务请求：
    http://127.0.0.1:7000/abc/abc1/consumer/depart/get/1
    就是请求到了： consumer-depart 的 consumer/depart/get/1 接口

## openfeign
* @EnableFeignClients
* 找到jar 包；
* META-INF/spring.factories:
  * org.springframework.cloud.openfeign.FeignAutoConfiguration
  * org.springframework.cloud.openfeign.FeignClientFactoryBean
  * org.springframework.cloud.openfeign.HystrixTargeter.target
## Gateway
* 找到jar 包；
* META-INF/spring.factories:
  * org.springframework.web.server.handler.DefaultWebFilterChain.filter
    org.springframework.web.server.handler.FilteringWebHandler.handle
    org.springframework.web.server.adapter.HttpWebHandlerAdapter.handle
    org.springframework.http.server.reactive.ReactorHttpHandlerAdapter.apply
  * org.springframework.web.reactive.DispatcherHandler.handle
  * org.springframework.web.reactive.handler.AbstractHandlerMapping.getHandler
  * org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping.getHandlerInternal
  * org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping.lookupRoute
  * org.springframework.web.reactive.DispatcherHandler.invokeHandler
  * org.springframework.web.reactive.result.SimpleHandlerAdapter.handle
  * org.springframework.cloud.gateway.handler.FilteringWebHandler.handle
  * org.springframework.cloud.gateway.handler.FilteringWebHandler.DefaultGatewayFilterChain.filter
  * org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory.apply
  * org.springframework.cloud.gateway.filter.NettyRoutingFilter.filter(远程请求)
  * org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter
## zipkin
### docker 安装
```
docker search zipkin
docker pull openzipkin/zipkin
# --restart always  表示重启
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```
### 使用例子：07-via-consumer-8080 07-via-provider-8081