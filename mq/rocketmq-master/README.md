## 说明：
### acl: 
    ACL是access control list的简称，俗称访问控制列表。访问控制，基本上会涉及到⽤户、资
    源、权限、⻆⾊等概念。
### broker: 
    broker模块(broker启动进程)
### client:
    包含 Producer 和 Consumer，负责消息的发和收，包含消息⽣产者、消息消费者相关类

### common:
    ⼀些公共代码，供其他模块依赖
### conf:
    配置⽂件
### dev:
    开发者信息(⾮源代码)
### distribution:
    ⼀些 sh 脚本和 配置，主要是在部署的时候⽤的(⾮源代码)
### example:
    RocketMQ示例代码，使⽤样例，包括各种使⽤⽅法，Pull模式、Push模式、⼴播模式、有序消息、事务消息等等
### filter:
    消息过滤相关基础类
### logappender:
    ⽇志相关
### namesrv:
    可以理解成注册中⼼，每个 broker 都会在这⾥注册，client 也会从这⾥获取 broker 的相关信息
### openmessaging:
    消息开放标准
### remoting:
    基于 Netty 实现的⽹络通信模块，包括 Server 和 Client, client、broker、
### filtersrv 
    等模块对它都有依赖
### srvutil:
    服务⼯具类
### store:
    消息存储实现相关类，负责消息的存储和读取
### style:
    代码模板，为了统⼀代码⻛格
### test:
    测试⽤例
### tools:
    ⼀些⼯具类，基于它们可以写⼀些 sh ⼯具来管理、查看MQ系统的⼀些信息

## 启动
### 启动broker
    org.apache.rocketmq.broker.BrokerStartup.main
    添加参数：
    读取配置文件位置：
    -c D:\workspace\language\Java\learnCode\mq\rocketmq-master\broker\src\main\resources\broker.conf