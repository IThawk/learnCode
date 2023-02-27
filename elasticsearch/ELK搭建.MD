
# 搭建ELK
## docker启动es与kibana

docker run --restart=always -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

## windows
docker run --restart=always -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v d:/docker/data/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/docker/data/elasticsearch/data:/usr/share/elasticsearch/data -v d:/docker/data/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

```
# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please consult the documentation for further information on configuration options:
# https://www.elastic.co/guide/en/elasticsearch/reference/index.html
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
#
#cluster.name: my-application
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
#
#node.name: node-1
#
# Add custom attributes to the node:
#
#node.attr.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
#
#path.data: /path/to/data
#
# Path to log files:
#
#path.logs: /path/to/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
#
#bootstrap.memory_lock: true
#
# Make sure that the heap size is set to about half the memory available
# on the system and that the owner of the process is allowed to use this
# limit.
#
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
# Set the bind address to a specific IP (IPv4 or IPv6):
#
network.host: 0.0.0.0
#
# Set a custom port for HTTP:
#
http.port: 9200
#
# For more information, consult the network module documentation.
#
# --------------------------------- Discovery ----------------------------------
#
# Pass an initial list of hosts to perform discovery when this node is started:
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
#discovery.seed_hosts: ["host1", "host2"]
#
# Bootstrap the cluster using an initial set of master-eligible nodes:
#
#cluster.initial_master_nodes: ["node-1", "node-2"]
#
# For more information, consult the discovery and cluster formation module documentation.
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
#
#gateway.recover_after_nodes: 3
#
# For more information, consult the gateway module documentation.
#
# ---------------------------------- Various -----------------------------------
#
# Require explicit names when deleting indices:
#
#action.destructive_requires_name: true

```
## docker 启动kibana

docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.56.101:9200 -p 5601:5601 -d kibana:7.6.2


### windows
docker run --name kibana -v d:/docker/data/kibana/config/kibana.yml:/usr/share/kibana/config/kibana.yml -p 5601:5601 -d kibana:7.6.2

可以修改  ip地址
```
#
# ** THIS IS AN AUTO-GENERATED FILE **
#

# Default Kibana configuration for docker target
server.name: kibana
server.host: "0"
elasticsearch.hosts: [ "http://192.168.101.44:9200" ]
xpack.monitoring.ui.container.elasticsearch.enabled: true
```



### logstash

docker run --rm -it --name logstash --link elasticsearch -d -p 5044:5044  -v /mydata/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.6.2

#### logstash.conf
```
  
    input {
      beats {
      port
      =>
      "5043"
    }
    }
    filter {
      if [
      fields
    ][
      doc_type
    ] == 'order' {
      grok {
      match
      => {
      "message"
      =>
      "%{TIMESTAMP_ISO8601:timestamp} %{LOGLEVEL:level} %{JAVALOGMESSAGE:msg}"
    }
    }
    }
    
    if [fields][doc_type] == 'customer' {# 这里写两个一样的grok，实际上可能出现多种不同的日志格式，这里做个提示而已,当然如果是相同的格式，这里可以不写的
    grok {
    match => {
    "message" => "%{TIMESTAMP_ISO8601:timestamp} %{LOGLEVEL:level} %{JAVALOGMESSAGE:msg}"}
    }
    }
    }
            
    output { stdout {codec => rubydebug} elasticsearch { hosts => ["localhost:9200"]
    index => "%{[fields][doc_type]}-%{+YYYY.MM.dd}"
    } }
```    
------------------  spring boot 直接 -------------------------------------
```
input {
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 5044
  }
}
output {
  elasticsearch {
    hosts => "172.29.132.191:9200"
    index => "springboot-logstash-%{+YYYY.MM.dd}"
  }
}
```
### filebeat.yml
也可以不需要
```

# 日志输入配置
filebeat.inputs: - type: log enabled: true paths: # 需要收集的日志所在的位置，可使用通配符进行配置

# - /data/elk/*.log
- /logs/*/*.log
# 日志输出配置(采用 logstash 收集日志，5044为logstash端口)

output.logstash: hosts: ['192.168.56.101:5044']

```

docker run --name filebeat -d --link logstash -v /mydata/logs:/logs -v /mydata/filebeat/filebeat.yml:/usr/share/filebeat/filebeat.yml elastic/filebeat:7.6.2


#### logstash对接springboot
```xml
    <dependencies>
        <!--logstash 依赖-->
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>5.2</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.11.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.11.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.2</version>
        </dependency>
    </dependencies>
```
### 配置log配置信息（192.168.56.101:5044 logstash 启动的端口）
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml" />

    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>192.168.56.101:5044</destination>
        <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder" />
    </appender>

    <root level="INFO">
        <appender-ref ref="LOGSTASH" />
      <!--  <appender-ref ref="CONSOLE" />-->
    </root>
</configuration>
```
### kibana 中需要去management中添加index pattern 添加索引

![image-20210816103401531](ElasticSearch7.14.assets/img.png)


### 添加定时清理
```

#!/bin/bash
# @Author: richard
# @Date:   2017-08-11 17:27:49
# @Last Modified by:   richard
# @Last Modified time: 2017-08-11 18:04:58
#保留近 N 天
KEEP_DAYS=7 
# 删除前 N的所有天到 前N+10天==>每天执行
function get_todelete_days()
{
    # declare -A DAY_ARR
    # DAY_ARR=""
    for i in $(seq 1 10);
    do
        THIS_DAY=$(date -d "$(($KEEP_DAYS+$i)) day ago" +%Y.%m.%d)
 
        DAY_ARR=( "${DAY_ARR[@]}" $THIS_DAY)
    done
    echo ${DAY_ARR[*]} 
}
# 返回数组的写法
TO_DELETE_DAYS=(`get_todelete_days`)
for day in "${TO_DELETE_DAYS[@]}"
do
    echo "$day will be delete"  
    curl -XDELETE 'http://127.0.0.1:9200/*-'${day}
done

```
在目录下启动定时任务执行此文件，

输入：crontab -e

输入内容：

30 23 * * 7 /opt/springboot/elasticsearch/delete-es/es-del.sh

然后按Esc输入  :wq   即可。

每周日晚上23：30执行一次。

定时任务执行log文件地址：/var/spool/mail/root 可查看执行错误信息。