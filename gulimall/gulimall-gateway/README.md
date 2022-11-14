# spring-gateway 网关
https://spring.io/projects/spring-cloud-gateway
https://docs.spring.io/spring-cloud-gateway/docs/2.2.5.RELEASE/reference/html/
官方文档 包含所有的路由转发规则
## 配置
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: after_route
        uri: https://example.org
        predicates:
        - Cookie=mycookie,mycookievalue
```