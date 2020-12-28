docker run --name elasticsearch -p 19200:9200 -p 19300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -v d:/linux/es/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/linux/es/data:/usr/share/elasticsearch/data -v  d:/linux/es/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2

mkdir -p /programs/elasticsearch/config
mkdir -p /programs/elasticsearch/data
mkdir -p /programs/elasticsearch/plugins
echo "http.host: 0.0.0.0" >> /programs/elasticsearch/config/elasticsearch.yml
chmod -R 777 /programs/elasticsearch/
docker run --name elasticsearch -p 9200:9200 -p 9300:9300  -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx128m" -v /programs/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /programs/elasticsearch/data:/usr/share/elasticsearch/data -v /programs/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.6.2


如果出现权限问题，只能下面办法启动
docker run --name elasticsearch -p 19200:9200 -p 19300:9300 -e  "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" -d elasticsearch:7.6.2
