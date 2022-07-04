docker run --privileged=true -d --name zookeeper --publish 2181:12181  -d zookeeper:latest


docker run --name postgres --privileged=true -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d postgres:9.2.21