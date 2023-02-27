package com.baizhi.test;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * 分页  排序  返回指定字段  高亮查询
 */
public class TestHighSearch extends ElasticsearchDemoApplicationTests{

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestHighSearch(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Test
    public void testSearch() throws IOException {
        //参数 1: 在那个索引中搜索
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .query(QueryBuilders.termQuery("description","不错")) //执行条件
                .sort("price", SortOrder.ASC)//执行排序字段 和 排序类型
                .fetchSource(new String[]{},new String[]{"id"})//参数 1:包含那些字段 //参数 2: 去掉那些字段
                .from(1) //起始条数 (当前页-1)*size
                .size(1) //每页展示条数
                .highlighter(new HighlightBuilder().field("description").preTags("<span style='color:red'>").postTags("</span>"));//高亮展示
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(searchResponse.getHits().getTotalHits().value);
        System.out.println(searchResponse.getHits().getMaxScore());

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            //获取原始内容
            System.out.println(hit.getId()+"  "+hit.getScore()+"   "+ hit.getSourceAsString());
            //获取高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields.get("description").fragments()[0].toString());//获取高亮结果
        }
        
    }
}
