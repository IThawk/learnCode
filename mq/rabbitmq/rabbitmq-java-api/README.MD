# RabbitMQ Java API编程
需要在本机安装Erlang、RabbitMQ并运行。

RabbitMQ默认端口：5672。

# docker 安装
```
docker pull rabbitmq:management
docker run -dit --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:management
```
http://192.168.56.101:15672/#/  guest   guest
http://127.0.0.1:25672/#/  guest   guest
## simple 
添加 /my_vhost
添加 SIMPLE_EXCHANGE durable 设置为false
# 目录package说明
  ack  消息手工确认、不确认、拒绝和重回队列的测试

  confirm 生产者发送确认的测试

  dlx 死信队列的测试

  limit 消费者限流的测试

  returnlistener 当消息无法匹配到队列时，会发回给生产者

  rpc RPC测试

  simple 简单收发消息测试

  transaction 生产者发送事务的测试

  ttl 消息存活时间测试



  基于延时插件实现的延时队列（需连接到Linux系统的服务），需要安装延时队列插件：

  $ cd /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.12/plugins

  $ wget https://bintray.com/rabbitmq/community-plugins/download_file?file_path=rabbitmq_delayed_message_exchange-0.0.1.ez

  $ mv download_file?file_path=rabbitmq_delayed_message_exchange-0.0.1.ez 

rabbitmq_delayed_message_exchange-0.0.1.ez

  $ rabbitmq-plugins enable rabbitmq_delayed_message_exchange

