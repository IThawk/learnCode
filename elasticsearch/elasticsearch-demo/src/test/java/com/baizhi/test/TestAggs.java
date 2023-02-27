package com.baizhi.test;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class TestAggs extends ElasticsearchDemoApplicationTests{

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestAggs(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Test
    public void testAggsPrice() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.terms("price_group").field("price")).size(0);//聚合处理
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //获取聚合结果
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDoubleTerms parsedDoubleTerms = aggregations.get("price_group");
        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey()+"  "+bucket.getDocCount());
        }
    }
    @Test
    public void testAggsTitle() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.terms("title_group").field("title")).size(0);//聚合处理
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //获取聚合结果
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedStringTerms parsedStringTerms = aggregations.get("title_group");
        List<? extends Terms.Bucket> buckets = parsedStringTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey()+"  "+bucket.getDocCount());
        }
    }

    //min max avg sum
    @Test
    public void testSumPrice() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //sum
        //sourceBuilder.aggregation(AggregationBuilders.sum("price_sum").field("price")).size(0);//聚合处理
        //min
        //sourceBuilder.aggregation(AggregationBuilders.min("price_min").field("price")).size(0);//聚合处理
        //max
        //sourceBuilder.aggregation(AggregationBuilders.max("price_max").field("price")).size(0);//聚合处理
        //avg
        sourceBuilder.query(QueryBuilders.termQuery("description","非常")).aggregation(AggregationBuilders.avg("price_avg").field("price"));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //获取聚合结果
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedAvg parsedAvg = aggregations.get("price_avg");
        System.out.println(parsedAvg.getValue());


    }
}
