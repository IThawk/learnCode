package com.ithawk.demo.elasticsearch.springboot.test;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.elasticsearch.springboot.ElasticSearchApplication;
import com.ithawk.demo.elasticsearch.springboot.config.ElasticSearchConfig;
import com.ithawk.demo.elasticsearch.springboot.pojo.Car;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.GeoDistanceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.ParsedGeoDistance;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class ElasticSearchApplicationTest {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * elasticsearch 搜索
     * @throws IOException
     */
    @Test
    public void search() throws IOException {
        //创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //检索的索引
        searchRequest.indices("user");
        //设置检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.matchQuery("address","ss"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(response);
    }

    /**
     * 添加索引，数据
     * @throws IOException
     */
    @Test
    public void addIndex() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        User user = new User();
        user.setUsername("test");
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(indexResponse);
    }
    class User{
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        private  String username;
    }

    /**
     * 查询索引数据
     * @throws IOException
     */
    @Test
    public void getIndex() throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(searchResponse);
    }

    /**
     * 查询索引数据
     * @throws IOException
     */
    @Test
    public void getIndexWithQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(searchResponse);
    }

    /**
     * 统计一定范围内坐标点的个数
     */
    @Test
    public void geo_distance_aggs() {

        GeoPoint position = new GeoPoint(32.026904,  118.795254);
//        GeoPoint position = new GeoPoint(32.027042,  118.79549);

        GeoDistanceAggregationBuilder tongjiAggs = AggregationBuilders
                            .geoDistance("tongji", position)
                            .field("geo")
                            .unit(DistanceUnit.KILOMETERS)
                            .distanceType(GeoDistance.PLANE)
                            .addRange(0, 1)
                            .addRange(1, 5)
                            .addRange(5, 100);

        /***** es7.x *****/
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(tongjiAggs)
                .build();

        /***** es6.x *****/
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .addAggregation(tongjiAggs)
//                .build();

        SearchHits<Car> searchHits = esTemplate.search(query, Car.class);
        Aggregations aggregations = searchHits.getAggregations();

//        Aggregations aggregations =
//                esTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//                    @Override
//                    public Aggregations extract(SearchResponse response) {
//                        return response.getAggregations();
//                    }
//                });

        ParsedGeoDistance parsedGeoDistance = aggregations.get("tongji");
        List bucketList = parsedGeoDistance.getBuckets();

        System.out.println(bucketList);
        for (int i = 0 ; i < bucketList.size() ; i ++) {
            ParsedGeoDistance.ParsedBucket bucket = (ParsedGeoDistance.ParsedBucket) bucketList.get(i);
            long docCount = bucket.getDocCount();
            String key = bucket.getKey();

            System.out.println("key: " + key);
            System.out.println("docCount: " + docCount);
        }

    }
}
