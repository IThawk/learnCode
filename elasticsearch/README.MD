# docker启动es与kibana

docker run --restart=always -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.56.101:9200 -p 5601:5601 -d kibana:7.6.2

# log

docker run --rm -it --name logstash --link elasticsearch -d -p 5044:5044  -v /mydata/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.6.2

## logstash.conf

  
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
------------------  spring boot 直接 -------------------------------------
    input {
      tcp {
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
    index => "logstash-%{+YYYY.MM.dd}"
    } }

### filebeat.yml

```

# 日志输入配置
filebeat.inputs: - type: log enabled: true paths: # 需要收集的日志所在的位置，可使用通配符进行配置

# - /data/elk/*.log
- /logs/*/*.log
# 日志输出配置(采用 logstash 收集日志，5044为logstash端口)

output.logstash: hosts: ['192.168.56.101:5044']

```

docker run --name filebeat -d --link logstash -v /mydata/logs:/logs -v /mydata/filebeat/filebeat.yml:/usr/share/filebeat/filebeat.yml elastic/filebeat:7.6.2


## logstash对接springboot
```xml
    <dependencies>
        <!--springboot web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.3.0.RELEASE</version>
        </dependency>
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
  添加索引


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
GET _search

```json
{
  "query": {
    "match_all": {}
  }
}
```

GET /energy/_search

```json
{
  "query": {
    "match_all": {}
  }
}
```

# 创建索引为 user1

PUT /user1/

```json
{
  "settings": {
    "index": {
      "number_of_shards": 3,
      "number_of_replicas": 0
    }
  }
}
```

# 添加、修改 _id 为1的数据 类型为_doc 文档

PUT /user1/_doc/1

``` json
{
"first_name": "Jane",
"last_name": "Smith",
"age": 32,
"about": "I like to collect rock albums",
"interests": ["music"]
}
```

# 获取 索引：user

GET /user1/

# 获取 索引：user 索引的配置

GET /user1/_settings

# 获取 所有索引的配置

GET /_all/_settings

# 获取 索引：user 类型：_doc id:1 的数据

GET /user1/_doc/1

# 查看文档的部分信息

GET /user1/_doc/1?_source=age,about

# 修改文档中的指定字段的值

POST /user1/_doc/1/_update

``` json
{
"doc":{
"age":33
}
}
```

# 删除文档

DELETE /user1/_doc/1

# 删除一个索引

DELETE user1

```

### 1:创建索引index和映射mapping

* 请求url： PUT localhost:9200/blog1
* 请求体：

```json
{
  "settings": {
    "index": {
      "number_of_shards": "5",
      "number_of_replicas": "1"
    }
  },
  "mappings": {
    "_doc": {
      "properties": {
        "id": {
          "type": "long",
          "store": true,
          "index": true
        },
        "title": {
          "type": "text",
          "store": true,
          "index": true,
          "analyzer": "standard"
        },
        "content": {
          "type": "text",
          "store": true,
          "index": true,
          "analyzer": "standard"
        }
      }
    }
  }
}
```

### 3 创建索引后设置Mapping 我们可以在创建索引时设置mapping信息，当然也可以先创建索引然后再设置mapping。 在上一个步骤中不设置maping信息，直接使用put方法创建一个索引，然后设置mapping信息。

* 请求的url： POST http://127.0.0.1:9200/blog2/_doc/_mapping
* 请求体：

```json
{
  "_doc": {
    "properties": {
      "id": {
        "type": "long",
        "store": true
      },
      "title": {
        "type": "text",
        "store": true,
        "index": true,
        "analyzer": "standard"
      },
      "content": {
        "type": "text",
        "store": true,
        "index": true,
        "analyzer": "standard"
      }
    }
  }
}
```

### 4 删除索引index

* 请求url：DELETE localhost:9200/blog1

### 5 创建文档document

* 请求url：POST localhost:9200/blog1/_doc/1
* 请求体：

```json
{
  "id": 1,
  "title": "ElasticSearch是一个基于Lucene的搜索服务器",
  "content": "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。"
}
```

### 6 修改文档document

* 请求url：POST/PUT localhost:9200/blog1/_doc/1
* 请求体：

```json
{
  "id": 1,
  "title": "【修改】ElasticSearch是一个基于Lucene的搜索服务器",
  "content": "【修改】它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。"
}
```

### 7 删除文档document

*请求url：DELETE localhost:9200/blog1/_doc/1

### 8 根据id查询

* 请求url：GET localhost:9200/blog1/_doc/1

## 八、查询表达式(Query DSL)

### 1 查询全部

* 请求url：POST http://localhost:9200/_search(所有索引)  http://localhost:9200/blog1/_search(blog1索引)
* 请求体：

```json
{
  "query": {
    "match_all": {}
  }
}
```

### 2 term查询 （不允许分词） 查询指定字段的关键词索引中的结果

* 请求url：POST localhost:9200/blog1/_doc/_search
* 请求体：

```json
{
  "query": {
    "term": {
      "title": "搜索"
    }
  }
}
```

* 请求体：先分词后查询，相关度排序：TF:越高越重要 ，DF：越高，多个文档出现越多，越不重要 score = X*TF/DF

```json
{
  "query": {
    "match": {
      "title": "title java是什么"
    }
  }
}
```

### 3 querystring查询

* 请求url：POST localhost:9200/blog1/article/_search
* 请求体：

```json
{
  "query": {
    "query_string": {
      "default_field": "title",
      "query": "搜索服务器"
    }
  }
}
```

### 4 multi_match查询 可以在多个字段上查询

* 请求url：POST localhost:9200/blog1/article/_search
* 请求体：

```json
{
  "query": {
    "multi_match": {
      "query": "李四",
      "fields": [
        "name",
        "address"
      ]
    }
  }
} 
```

5 bool查询

* bool （布尔）过滤器。 这是个 复合过滤器（compound filter） ， 它可以接受多个其他过滤器作为参 数，并将这些过滤器结合成各式各样的布尔（逻辑）组合。 一个 bool 过滤器由三部分组成： 实例： 配成功。

```json
{
  "bool": {
    "must": [],
    "should": [],
    "must_not": [],
    "filter": []
  }
}
```

```json
{
  "query": {
    "bool": {
      "must": {
        "query_string": {
          "query": "李四",
          "default_field": "name"
        }
      },
      "should": {
        "term": {
          "address": "上海"
        }
      },
      "filter": {
        "query_string": {
          "query": "男",
          "default_field": "sex"
        }
      }
    }
  }
} 
```

* must ：语句指明了，对于一个文档，所有的查询都必须为真，这个文档才能够匹
* should ：语句指明，对于一个文档，查询列表中，只要有一个查询匹配，那么这个文档就被看成是匹 配的。
* must_not ：语句指明，对于一个文档，查询列表中的的所有查询都必须都不为真，这个文档才被认为 是匹配的。
* filter ：从ES5.0开始过滤条件需要在添加在bool查询中，filter条件中支持查询条件中的所有查询方 法。filter可以配置多个，从而实现多个过滤条件同时生效。 九、 IK 分词器和ElasticSearch集成使用
  5.1 上述查询存在问题分析 在进行字符串查询时，我们发现去搜索"搜索服务器"和"钢索"都可以搜索到数据； 而在进行词条查询时，我们搜索"搜索"却没有搜索到数据；
  究其原因是ElasticSearch的标准分词器导致的，当我们创建索引时，字段默认使用的是标准分词器：

```json
{
  "query": {
    "bool": {
      "must": {
        "query_string": {
          "query": "李四",
          "default_field": "name"
        }
      },
      "filter": {
        "term": {
          "address": "上海"
        }
      },
      "filter": {
        "query_string": {
          "query": "男",
          "default_field": "sex"
        }
      },
      "filter": {
        "term": {
          "id": "20"
        }
      }
    }
  }
}

```

### 安装ik分词器：

* https://github.com/medcl/elasticsearch-analysis-ik/ 下载zip包
* 将zip包解压到对应的plugins的目录下面
* 重启elasticsearch
* 查看分词器 GET _analyze

```json
{
  "analyzer": "ik_max_word",
  "text": "中国人最厉害"
}
,
{
"analyzer": "ik_max_word",
"text": "中国人最厉害"
}
```

```json
{
  "analyzer": "ik_smart",
  "text": "中国人最厉害"
}
```






