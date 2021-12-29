import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class QuickStart {

    public static void main(String[] args) {

        // 连接本地默认端口的Mongod
        // MongoClient mongoClient = MongoClients.create()

        // 连接远程服务的指定端口的Mongod
        // MongoClient mongoClient = MongoClients.create("mongodb://host1:27017");

        // 连接指定端口复制集
        // MongoClient mongoClient = MongoClients.create("mongodb://host1:27017,host2:27017,host3:27017/?replicaSet=myReplicaSet");

        // 连接 mongos路由: 连接一个
        MongoClient mongoClient = MongoClients.create( "mongodb://127.0.0.1:27017" );
//         MongoClient mongoClient = MongoClients.create( "mongodb://192.168.56.101:27017" );
        // 连接多个mongos路由
//        MongoClient mongoClient = MongoClients.create("mongodb://111.229.189.98:4000");


        //获取数据库
        MongoDatabase database = mongoClient.getDatabase("productdb");

        // 获取集合
        MongoCollection<Document> productdesc=database.getCollection( "productdesc" );

        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        productdesc.insertOne(doc);


        Bson eq = eq("name", "MongoDB");
        FindIterable<Document> find = productdesc.find(eq);
        Document first=find.first();
        System.out.println(first);

    }
}
