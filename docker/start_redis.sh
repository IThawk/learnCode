 sudo docker run -p 6379:6379 --name redis -v D:/docker/data/redis/redis.conf:/etc/redis/redis.conf -v D:/docker/data/redis/log:/log -v D:/docker/data/redis/data:/data -d redis:latest redis-server /etc/redis/redis.conf


docker run -p 6379:6379 --name redis --privileged=true -v /mydata/redis/redis.conf:/etc/redis/redis.conf -v/mydata/redis/log:/log -v /mydata/redis/data:/data -d redis:latest redis-server /etc/redis/redis.conf