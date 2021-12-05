docker run -d -p 10911:10911 -p 10909:10909 --name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -v D:/docker/data/rocketMq/broker.conf:/etc/rocketmq/broker.conf foxiswho/rocketmq:broker


mkdir -R /mydata/rocketMq

/mydata/rocketMq/broker.conf
brokerIP1=192.168.56.101
#这里的ip地址修改成自己服务器的ip地址
autoCreateTopicEnable=true
autoCreateSubscriptionGroup=true
listenPort=10911

chmod -R 777 /mydata/rocketMq

docker run -d -p 10911:10911 -p 10909:10909 --privileged=true --name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -v /mydata/rocketMq/broker.conf:/etc/rocketmq/broker.conf foxiswho/rocketmq:broker