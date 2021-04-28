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
