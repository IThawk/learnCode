docker run --name mysql -p 3306:3306 -v /mydata/mysql:/var/lib/mysql --privileged=true -e MYSQL_ROOT_PASSWORD="123456" -d mysql:5.7

docker run --name mysql -p 3306:3306  --privileged=true -e MYSQL_ROOT_PASSWORD="123456" -d mysql:5.7