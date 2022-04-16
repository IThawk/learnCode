# rabbitMq
## 环境添加：docker 启动kafka
    docker run -d --name rabbitmq -p 15672:5672 -p 25672:15672 --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.8.9-management
 登入：
 http://127.0.0.1:25672/#/   admin:admin
