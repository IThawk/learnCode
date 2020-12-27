##rocketmq centos 单机版搭建

cd /etc/init.d

vi rocketmq

chmod +x rocketmq

启动：
service rocketmq startnamesrv

service rocketmq startbroker

service rocketmq startconsole

http://192.168.56.101:19876/
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