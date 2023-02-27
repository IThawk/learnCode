package com.baizhi.test;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * 文档检索操作
 */
public class TestSearchDocument extends ElasticsearchDemoApplicationTests {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestSearchDocument(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    @Test
    public void testQuery() throws IOException {
        //fuzzyQuery 模糊查询
        //query(QueryBuilders.fuzzyQuery("description", "很不错啦"));

        //ids
        //query(QueryBuilders.idsQuery().addIds("1","2","3"));

        //range
        //query(QueryBuilders.rangeQuery("price").gte(1).lte(6));

        //wildcardQuery
        //query(QueryBuilders.wildcardQuery("description","日本*"));

        //bool
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //must 同时成立
        //queryBuilder.must(QueryBuilders.termQuery("description","不错"));
        //queryBuilder.must(QueryBuilders.termQuery("title","日本豆"));

        //should 满足一个即可
        queryBuilder.should(QueryBuilders.idsQuery().addIds("1"));
        queryBuilder.should(QueryBuilders.idsQuery().addIds("2"));

        //must not 必须不能满足
        queryBuilder.mustNot(QueryBuilders.termQuery("title","日本豆"));
        //query(queryBuilder);

        // multi  match
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery("描述很不错");
        multiMatchQuery.field("title");
        multiMatchQuery.field("description");
        query(multiMatchQuery);

        // query string
        query(QueryBuilders.queryStringQuery("心情不错").field("description"));


    }

    //termQuery 基于关键词查询
    @Test
    public void testTermQuery() throws IOException {
        query(QueryBuilders.termQuery("description", "很不错"));
    }

    //查询所有
    @Test
    public void testAll() throws IOException {
        query(QueryBuilders.matchAllQuery());
    }

    //用来测试查询方法
    public void query(QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取总条数
        System.out.println("符合条件总条数: " + searchResponse.getHits().getTotalHits().value);
        //获取符合条件文档的最高分
        System.out.println("文档的最高分: " + searchResponse.getHits().getMaxScore());
        //获取符合条件内容
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("当前文档的 id: " + hit.getId() + " 当前文档的得分: " + hit.getScore() + " 当前文档的内容: " + hit.getSourceAsString());
        }
    }


    //根据 id 查询一条文档
    @Test
    public void testSearchById() throws IOException {
        GetResponse getResponse = restHighLevelClient.get(new GetRequest("products", "1"), RequestOptions.DEFAULT);
        System.out.println(getResponse.getIndex());
        System.out.println(getResponse.getType());
        System.out.println(getResponse.getVersion());
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
        System.out.println(sourceAsMap.get("id"));
        System.out.println(sourceAsMap.get("title"));
        System.out.println(sourceAsMap.get("price"));
        System.out.println(sourceAsMap.get("description"));
        System.out.println(getResponse.getSourceAsString()); //json 格式返回值
    }

}
