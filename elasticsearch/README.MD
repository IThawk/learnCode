
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










