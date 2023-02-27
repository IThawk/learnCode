package com.baizhi.test;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 文档增删改 批量操作
 */
public class TestDocument extends ElasticsearchDemoApplicationTests{

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestDocument(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    //4.批量操作
    @Test
    public void testBulkDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        //添加
        IndexRequest indexRequest = new IndexRequest("products");
        indexRequest.id("3").source("{\"id\":2,\"title\":\"我是 3 号\",\"price\":10.2,\"description\":\"我是3 号的描述!\"}",XContentType.JSON);
        bulkRequest.add(indexRequest);
        //更新
        UpdateRequest updateRequest = new UpdateRequest("products","2");
        updateRequest.doc("{\"price\":5.5}",XContentType.JSON);
        bulkRequest.add(updateRequest);

        //删除
        //bulkRequest.add(new DeleteRequest("products",""));

        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        for (BulkItemResponse bulkItemRespons : bulkItemResponses) {
            System.out.println("执行结果: "+bulkItemRespons.status());
        }
    }

    //3.删除一条文档
    @Test
    public void testDeleteDocument() throws IOException {
        //参数 1: 删除索引  参数 2: 删除文档 id
        DeleteRequest deleteRequest = new DeleteRequest("products", "2");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("是否删除成功 :" + deleteResponse.status());
    }

    //2.更新一条文档
    @Test
    public void testUpdateDocument() throws IOException {
        //参数1:  更新的索引名称  //参数 2: 更新文档 id
        UpdateRequest updateRequest = new UpdateRequest("products","1");
        updateRequest.doc("{\"description\":\"小浣熊真不错!!!\"}",XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("是否更新成功 : " + updateResponse.status());
    }

    //1.创建一条文档
    @Test
    public void testCreateDocument() throws IOException {
        //参数 1: 向哪个索引插入文档
        IndexRequest indexRequest = new IndexRequest("products");
        indexRequest//.id("2") //手动指定文档 id 为 2  注意: 指定 id 使用声明 id 不指定 id 就自动生成 id
                .source("{\n" +
                        "  \"id\":3,\n" +
                        "  \"title\":\"小日本豆\",\n" +
                        "  \"price\":3.5,\n" +
                        "  \"description\":\"日本豆真不错!!!\"\n" +
                        "}", XContentType.JSON);//代表文档内容
        //索引一条文档 参数 1:索引请求对象 参数 2:请求配置对象
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("是否索引成功: "+indexResponse.status());
    }
}
