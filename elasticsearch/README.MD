
# docker启动es与kibana
docker run --restart=always -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data -v  /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2


docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.56.101:9200 -p 5601:5601 -d kibana:7.6.2

# log
docker run --rm -it --name logstash --link elasticsearch -d -v /mydata/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.6.2
## logstash.conf
```json
input {
  beats {
    host => "localhost"
    port => "5043"
  }
}
filter {
   if [fields][doc_type] == 'order' {
    grok {
            match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} %{LOGLEVEL:level} %{JAVALOGMESSAGE:msg}" }
        }
   }

   if [fields][doc_type] == 'customer' { # 这里写两个一样的grok，实际上可能出现多种不同的日志格式，这里做个提示而已,当然如果是相同的格式，这里可以不写的
    grok {
            match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} %{LOGLEVEL:level} %{JAVALOGMESSAGE:msg}" }
        }
   }
}

output {
  stdout { codec => rubydebug }
  elasticsearch {
        hosts => [ "localhost:9200" ]
        index => "%{[fields][doc_type]}-%{+YYYY.MM.dd}"
    }
}

```

### filebeat.yml
```json
# 日志输入配置
filebeat.inputs:
- type: log
  enabled: true
  paths:
  # 需要收集的日志所在的位置，可使用通配符进行配置
  #- /data/elk/*.log
    - /logs/*/*.log

#日志输出配置(采用 logstash 收集日志，5044为logstash端口)
output.logstash:
hosts: ['192.168.56.101:5044']


```

docker run --name filebeat -d --link logstash -v /mydata/logs:/logs  -v /mydata/filebeat/filebeat.yml:/usr/share/filebeat/filebeat.yml elastic/filebeat:7.6.2
## 使用kibana界面操作dev_tools 修改elasticsearch数据


例子：
http://192.168.56.101:5601/app/kibana#/dev_tools/console
```
GET _search
{
"query": {
"match_all": {}
}
}

GET /energy/_search
{
"query": {
"match_all": {}
}
}

# 创建索引为 user1
PUT /user1/
{
"settings":{
"index":{
"number_of_shards":3,
"number_of_replicas":0
}
}
}

# 添加、修改 _id 为1的数据 类型为_doc 文档
PUT /user1/_doc/1
{
"first_name": "Jane",
"last_name": "Smith",
"age": 32,
"about": "I like to collect rock albums",
"interests": ["music"]
}


# 获取 索引：user
GET /user1/

# 获取 索引：user  索引的配置
GET /user1/_settings

# 获取 所有索引的配置
GET /_all/_settings

# 获取 索引：user  类型：_doc id:1 的数据
GET /user1/_doc/1

# 查看文档的部分信息
GET /user1/_doc/1?_source=age,about


# 修改文档中的指定字段的值
POST /user1/_doc/1/_update
{
"doc":{
"age":33
}
}

# 删除文档
DELETE /user1/_doc/1

#删除一个索引
DELETE user1
```










