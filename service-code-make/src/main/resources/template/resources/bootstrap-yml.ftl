#logging:
#  config: classpath:log4j.xml
#  level:
#    cn.jay.repository: trace
server:
  port: 8088
spring:
  datasource:
    url: jdbc:mysql://192.168.56.101:3306/micromall?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
    username: root
    password: 123456
    druid:
      initial-size: 5 #连接池初始化大小
      min-idle: 10 #最小空闲连接数
      max-active: 20 #最大连接数
      web-stat-filter:
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*" #不统计这些请求数据
        enabled: true #是否启用StatFilter默认值false
      stat-view-servlet: #访问监控网页的登录用户名和密码
        login-username: druid
        login-password: druid
        enabled: true # 默认关闭了管理面
      filter:
        config:
          enabled: true
  application:
    name: service-elasticsearch #微服务名称，对应dataId
  profiles:
    active: dev
  cloud:
    nacos:
      config: #配置中心服务地址
        server-addr: 192.168.56.101:8848
        file-extension: yml  #配置中心文件类型
        encode: utf-8     #配置中心编码
      discovery:
        server-addr: 192.168.56.101:8848
management:
  endpoints:
    web:
      exposure:
        include: '*'
mybatis:
  mapper-locations:
    - classpath:mapper/*.xml
    - classpath*:com/**/mapper/*.xml
  type-aliases-package: com.ithawk.mgr.dao
elasticsearch:
  cluster_host: 192.168.56.101
  eNode1_port: 9200
