##rocketmq centos 单机版搭建

cd /etc/init.d

vi rocketmq

chmod +x rocketmq

启动：
service rocketmq startnamesrv

service rocketmq startbroker

service rocketmq startconsole

http://192.168.56.101:19876/   admin admin
```
#!/bin/bash
#chkconfig:2345 20 90
#description:rocketmq
#processname:rocketmq
RQ_PATH=/programs/rocketmq-all-4.8.0-bin-release/
export JAVA_HOME=/programs/jdk1.8.0_161
case $1 in
         startbroker) nohup sh  $RQ_PATH/bin/mqbroker -n localhost:9876 autoCreateTopicEnable=true > /logs/mqbroker.log 2>&1 &;;
         startnamesrv) nohup sh  $RQ_PATH/bin/mqnamesrv > /logs/mqnamesrv.log 2>&1 &;;
         stopnamesrv)  sh  $RQ_PATH/bin/mqshutdown namesrv;;
         stopbroker) sh  $RQ_PATH/bin/mqshutdown broker;;
         startconsole) sh /programs/rocketmq-console/start.sh > /logs/mqconsole.log 2>&1 &;;
         *)  echo "require startbroker|startnamesrv|stopnamesrv|stopbroker|startconsole"  ;;
esac

```
# 使用docker安装rocketMq

```
docker run --name rmqnamesrv -d -p 9876:9876 rocketmqinc/rocketmq:4.4.0 sh mqnamesrv




mkdir -R /mydata/rocketMq

/mydata/rocketMq/broker.conf
brokerIP1=192.168.56.101
#这里的ip地址修改成自己服务器的ip地址
autoCreateTopicEnable=true
autoCreateSubscriptionGroup=true
listenPort=10911

chmod -R 777 /mydata/rocketMq

docker run -d -p 10911:10911 -p 10909:10909 --privileged=true --name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -v /mydata/rocketMq/broker.conf:/etc/rocketmq/broker.conf foxiswho/rocketmq:broker



docker run --name rmqconsole -d -p 18080:8080 --link rmqnamesrv:namesrv -e "JAVA_OPTS=-Drocketmq.namesrv.addr=namesrv:9876"  pangliang/rocketmq-console-ng
```

## SWAGGER 
http://127.0.0.1:8080/swagger-ui/index.htm

## 源码构建
将源码导入IDEA后，需要先对源码进行编译。编译指令 clean install -Dmaven.test.skip=true
先在项目目录下创建一个conf目录，并从distribution拷贝 broker.conf和logback_broker.xml和logback_namesrv.xml

#### 启动
#### nameServer
展开namesrv模块，运行NamesrvStartup类即可启动NameServer

#### broker
展开broker模块，运行BrokerStartup类即可启动BrokerController

提示需要配置一个ROCKETMQ_HOME环境变量(这样才会有日志：D:\logs\rocketmqlogs)
ROCKETMQ_HOME=D:\workspace\language\github\learnCode\mq\rocketmq\rocketmq-all-4.7.1-source-release
