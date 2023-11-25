# rabbitMq
## 环境添加：docker 启动kafka
    docker run -d --name rabbitmq -p 15672:5672 -p 25672:15672 --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.8.9-management
 登入：
 http://127.0.0.1:25672/#/   admin:admin
 direct（默认方式）：最基础最简单的模式，发送方把消息发送给订阅方，如果有多个订阅者，默认采取轮询的方式进行消息发送。
 headers：与 direct 类似，只是性能很差，此类型几乎用不到。
 fanout：分发模式，把消费分发给所有订阅者。
 topic：匹配订阅模式，使用正则匹配到消息队列，能匹配到的都能接收到。