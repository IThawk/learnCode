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

### 代码总结：
#### 获取启动参数代码：
```xml
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.4</version>
        </dependency>
```
```java
import org.apache.commons.cli.*;

public class CommandLineUtil {


    //从启动命令中获取 启动参数。
    public static CommandLine buildCommandlineOptions(final Options options, String[] args) {
        //添加一个option hasArg:是否从启动中获取
        Option opt = new Option("h", "help", true, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("n", "name", true,
                "Name ");
        opt.setRequired(false);
        options.addOption(opt);
        CommandLineParser parser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return commandLine;
    }


    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("c", "help", true, "Print help"));
        CommandLine commandLine = buildCommandlineOptions(options, args);
        if (commandLine.hasOption('h')) {
            String file = commandLine.getOptionValue('h');
            System.out.println(file);
        }


    }
}

```

## 启动

### 启动NameServer

    org.apache.rocketmq.namesrv.NamesrvStartup main
    NameServer：整个消息系统的状态服务器，例如选举、同步等。NameServer 本身是⽆状态的，
    可以部署多个，相互之间没有数据交互。它主要⽤于管理 Broker 集群信息（提供⼼跳检查，检查是否
    有 Broker 下线） 和 Topic 路由信息（⽤于客户端查询）。

### 启动broker

    org.apache.rocketmq.broker.BrokerStartup.main
    添加启动参数：
    读取配置文件位置：
    -c D:\workspace\language\Java\learnCode\mq\rocketmq-master\broker\src\main\resources\broker.conf

    broker 启动netty org.apache.rocketmq.remoting.netty.NettyRemotingServer.start

### producer

    消息发送： org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl

### 消息存储

#### ⽂件进⾏逐⼀介绍：

    1.commitlog: 消息存储⽬录；
    2.config: 运⾏期间⼀些配置信息，主要包括如下信息：
    2.1 consumerFilter.json : 主题消息过滤信息
    2.2 consumerOffset.json : 集群消费模式消息消费进度
    2.3 delayOffset.json : 延时消息队列拉取进度
    2.4 subscriptionGroup.json : 消息消费组配置信息
    2.5 topic.json : 配置属性
    3.consumequeue : 消息消费队列存储⽬录
    4.index : 消息索引⽂件存储⽬录，按key、tag、时间等存储
    5.abort : 如果存在abort⽂件说明Broker⾮正常关闭，该⽂件默认启动时候创建，正常退出之前删
    除
    6.checkpoint : ⽂件检测点，存储commitlog⽂件最后⼀次刷盘时间戳、consumequeue最后⼀次
    刷盘时间、index索引⽂件最后⼀次刷盘时间戳。

#### 整体步骤

    1. 要发送的消息，会按顺序写⼊commitlog中，这⾥所有topic和queue共享⼀个⽂件
    2. 存⼊commitlog后，由于消息会按照topic维度来消费，会异步构建consumeQueue（逻辑队列）
       和index（索引⽂件），consumeQueue存储消息的commitlogOffset、messageSize、
       tagHashCode, ⽅便定位commitlog中的消息实体。每个 Topic下的每个Message Queue都有⼀个
       对应的ConsumeQueue⽂件。索引⽂件（Index）提供消息检索的能⼒，主要在问题排查和数据
       统计等场景应⽤
    3. 消费者会从consumeQueue取到msgOffset，⽅便快速取出消息
       优点
    1. CommitLog 顺序写 ，可以⼤⼤提⾼写⼊效率，提⾼堆积能⼒
    2. 虽然是随机读，但是利⽤操作系统的pagecache机制，可以批量地从磁盘读取，作为cache存到内
       存中，加速后续的读取速度
    3. 在实际情况中，⼤部分的 ConsumeQueue能够被全部读⼊内存，所以这个中间结构的操作速度很
       快， 可以认为是内存读取的速度
