docker run --name elasticsearch -p 19200:9200 -p 19300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v d:/linux/es/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/linux/es/data:/usr/share/elasticsearch/data -v  d:/linux/es/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

mkdir -p /mydata/elasticsearch/config
mkdir -p /mydata/elasticsearch/data
mkdir -p /mydata/elasticsearch/plugins
echo "network.host: 0.0.0.0" >> /mydata/elasticsearch/config/elasticsearch.yml
chmod -R 777 /mydata/elasticsearch/
docker run --name elasticsearch -p 9200:9200 -p 9300:9300 --privileged=true -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx128m" -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

解决文件权限问题 ：--privileged=true
如果出现权限问题，只能下面办法启动
docker run --name elasticsearch -p 9200:9200 -p 9300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -d elasticsearch:7.6.2
