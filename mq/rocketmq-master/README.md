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

```java
import org.apache.commons.cli.Options;

public class U {


    //从启动命令中获取 启动参数。
    public static Options buildCommandlineOptions(final Options options) {
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        opt =
                new Option("n", "namesrvAddr", true,
                        "Name server address list, eg: 192.168.0.1:9876;192.168.0.2:9876");
        opt.setRequired(false);
        options.addOption(opt);


        //加载配置文件中的数据
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        Properties properties = new Properties();
        properties.load(in);
        return options;
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
    添加参数：
    读取配置文件位置：
    -c D:\workspace\language\Java\learnCode\mq\rocketmq-master\broker\src\main\resources\broker.conf


