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
        <spring-cloud.version>Hoxton.SR1</spring-cloud.version>
        <spring-boot-revision>2.2.8.RELEASE</spring-boot-revision>
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
## zipkin
### docker 安装
```
docker search zipkin
docker pull openzipkin/zipkin
# --restart always  表示重启
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```
### 使用例子：07-via-consumer-8080 07-via-provider-8081