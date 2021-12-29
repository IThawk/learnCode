## mongo
docker run -p 27017:27017 -v /mydata/mongo:/data/db --name mongodb -d mongo:4.4.4
docker run -p 27017:27017 -v D:/docker/data/mongo:/data/db --name mongodb -d mongo:4.4.4
## 项目 
```sql
use admin;
show users;

db.createUser(
    {
        user: "gj",
        pwd: "gj123",
        roles: [ "root" ]
    }
);
```

## 知识点：
mvn dependency:tree >1.log
http://localhost:8080/swagger-ui/index.html

