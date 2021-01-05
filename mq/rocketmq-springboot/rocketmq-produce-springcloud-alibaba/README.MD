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