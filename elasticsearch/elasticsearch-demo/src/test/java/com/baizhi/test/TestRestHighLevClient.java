package com.baizhi.test;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * 索引和映射的操作
 */
public class TestRestHighLevClient extends ElasticsearchDemoApplicationTests{



    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestRestHighLevClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    //创建索引并创建映射
    @Test
    public void testCreateIndexAndMapping() throws IOException {
        //参数 1: 创建索引名称
        CreateIndexRequest indexRequest = new CreateIndexRequest("products");

        //创建映射
        //参数 1: source 代表映射 json 格式  参数 2: 代表数据格式类型 JSON
        indexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"id\":{\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"title\":{\n" +
                "        \"type\": \"keyword\"  \n" +
                "      },\n" +
                "      \"price\":{\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"description\":{\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);

        //参数 1: 创建索引请求对象  参数 2: 请求默认配置对象
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println("是否创建成功: "+createIndexResponse.isAcknowledged());
    }

    //删除索引
    @Test
    public void deleteIndex() throws IOException {
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(new DeleteIndexRequest("products"), RequestOptions.DEFAULT);
        System.out.println("是否删除成功: " + acknowledgedResponse.isAcknowledged());
    }
}
