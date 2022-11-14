#rocketMq spring-cloud-alibaba
## 配置说明：
```properties
# =
# 日志级别
logging.level.com.alibaba.cloud.stream.binder.rocketmq=DEBUG

spring.cloud.stream.rocketmq.binder.name-server=192.168.56.101:9876

spring.cloud.stream.bindings.output1.destination=test-topic
spring.cloud.stream.bindings.output1.content-type=application/json
spring.cloud.stream.rocketmq.bindings.output1.producer.group=binder-group
spring.cloud.stream.rocketmq.bindings.output1.producer.sync=true

spring.cloud.stream.bindings.output2.destination=TransactionTopic
spring.cloud.stream.bindings.output2.content-type=application/json
spring.cloud.stream.rocketmq.bindings.output2.producer.transactional=true
spring.cloud.stream.rocketmq.bindings.output2.producer.group=myTxProducerGroup
```



spring
```
基于目前业务需求需要提前将部分数据加载到Spring容器中。大家可以想一下解决方案，下面评论去留言或者私信溪源。溪源能够想到的解决方案：

定义静态常量，随着类的生命周期加载而提前加载（这种方式可能对于工作经验较少的伙伴，选择是最多的）；
实现CommandLineRunner接口；容器启动之后，加载实现类的逻辑资源，已达到完成资源初始化的任务；
@PostConstruct；在具体Bean的实例化过程中执行，@PostConstruct注解的方法，会在构造方法之后执行；
加载顺序为：Constructor > @Autowired > @PostConstruct > 静态方法；
特点：
只有一个非静态方法能使用此注解
被注解的方法不得有任何参数
被注解的方法返回值必须为void
被注解方法不得抛出已检查异常
此方法只会被执行一次
实现InitializingBean接口；重写afterPropertiesSet()方法；
以上方案供大家参考，提供一种解决思路。但是日常开发中有可能需要实现在项目启动后执行的功能，因此诞生了此篇文章。
思路：SpringBoot提供的一种简单的实现方案，实现CommandLineRunner接口，实现功能的代码放在实现的run方法中加载，并且如果多个类需要夹加载顺序，则实现类上使用@Order注解，且value值越小则优先级越高。

```