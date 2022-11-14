docker pull canal/canal-server:latest
docker run -p 11111:11111 -v /root/mydata/canal-server/conf/canal.properties:/home/admin/canal-server/conf/canal.properties --name canal -id canal/canal-server
vi /home/admin/canal-server/conf/canal.properties