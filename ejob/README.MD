docker
~~~
docker run --privileged=true -d --name zookeeper --publish 2181:12181  -d zookeeper:latest


docker run --name postgres --privileged=true -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d postgres:9.2.21

docker run -d -p 3306:3306 --privileged=true -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql:8.0
~~~