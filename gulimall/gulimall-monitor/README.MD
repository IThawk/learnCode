# 管理面界面登入
http://localhost:8101/#/applications
admin 123456
## 监控服务端
```yaml

# monitor管理面配置
spring:
  application:
    name: gulimall-monitor
  security: # 配置登录用户名和密码
    user:
      name: admin
      password: 123456
  boot:  # 不显示admin-server的监控信息
    admin:
      discovery:
        ignored-services: ${spring.application.name}
server:
  port: 8101
# monitor管理面配置
management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: ALWAYS
```
### 管理服务依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.1.0.RELEASE</version>
        </dependency>
        <!--spring-boot-admin 监控服务-->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.1.5</version>
        </dependency>
        <!-- 安全认证服务-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```

## 监控端接口
### 加入依赖包
```xml

        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.1.5</version>
        </dependency>
```
### 加入配置
```properties
spring.boot.admin.client.url=http://localhost:8101/monitor
management.endpoints.web.exposure.include=*
```
