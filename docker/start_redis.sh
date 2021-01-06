 sudo docker run -p 6379:6379 --name redis -v /mnt/d/redis/redis.conf:/etc/redis/redis.conf -v/mnt/d/redis/log:/log -v /mnt/d/redis/data:/data -d redis:latest redis-server /etc/redis/redis.conf --appendonly yes


docker run -p 6379:6379 --name redis --privileged=true -v /mydata/redis/redis.conf:/etc/redis/redis.conf -v/mydata/redis/log:/log -v /mydata/redis/data:/data -d redis:latest redis-server /etc/redis/redis.conf --appendonly yes