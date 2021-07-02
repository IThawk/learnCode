# skywalking
## docker 环境搭建：
### skywalking-oap-server
```
docker run --name skywalkinoap --restart always -d -e TZ=Asia/Shanghai -p 12800:12800 -p 11800:11800 --link elasticsearch:es7 -e SW_STORAGE=elasticsearch -e SW_STORAGE_ES_CLUSTER_NODES=es7:9200 apache/skywalking-oap-server:6.6.0-es7
```

### skywalking-ui
```
docker run -d --name skywalking-ui --restart=always -e TZ=Asia/Shanghai -p 8088:8080 --link skywalkingoap:oap -e SW_OAP_ADDRESS=192.168.56.101:12800 apache/skywalking-ui:6.6.0
```
### 页面
http://192.168.56.101:8088/
### 服务启动
```
下载源码包，下面会用到agent

https://archive.apache.org/dist/skywalking/6.6.0/apache-skywalking-apm-6.6.0.tar.gz

jvm 参数：
-javaagent:D://tools//skywalking//apache-skywalking-apm-bin//agent//skywalking-agent.jar
-Dskywalking.agent.service_name=MYtest
-Dskywalking.collector.backend_service=192.168.56.101:11800
```