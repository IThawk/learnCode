# kafka
## 环境添加：docker 启动kafka
    bus
    启动前提：启动zookeeper
    docker pull docker.io/wurstmeister/kafka
    
    docker run -d --name kafka -p 9092:9092 -v /mydata/kafka:/kafka -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.56.101:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.56.101:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
    
    
    docker pull docker.io/sheepkiller/kafka-manager
    docker run -d --name kafkamanager -p 19092:9000 -e ZK_HOSTS=192.168.56.101:2181  sheepkiller/kafka-manager
### 管理面地址：
    http://192.168.56.101:19092/
## kafka 知识点
![1547170701855](./images/kafka01.jpg)
### 高吞吐原因：
