package com.atguigu.gulimall.search.test;


import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.search.GUlimallSearchApplication;
import com.atguigu.gulimall.search.config.GuliMallElasticSearchConfig;
import com.atguigu.gulimall.search.pojo.Car;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.GeoDistanceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalGeoDistance;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.ParsedGeoDistance;
import org.elasticsearch.search.aggregations.metrics.ParsedGeoBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GUlimallSearchApplication.class)
public class GUlimallSearchApplicationTest {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void search() throws IOException {
        //创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        //设置检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "ss"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(response);

    }

    @Test
    public void addIndex() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        User user = new User();
        user.setUsername("test");
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(indexResponse);
    }

    class User {
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        private String username;
    }

    /**
     * 统计一定范围内坐标点的个数
     */
    @Test
    public void geo_distance_aggs() {

        GeoPoint position = new GeoPoint(32.026904, 118.795254);
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
        for (int i = 0; i < bucketList.size(); i++) {
            ParsedGeoDistance.ParsedBucket bucket = (ParsedGeoDistance.ParsedBucket) bucketList.get(i);
            long docCount = bucket.getDocCount();
            String key = bucket.getKey();

            System.out.println("key: " + key);
            System.out.println("docCount: " + docCount);
        }

    }

    /**
     * 创建索引 和 映射
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {

        //创建名称为blog2的索
        CreateIndexRequest request = new CreateIndexRequest("blog1");

        //设置映射 doc type名称
        request.mapping(" {\n" +
                " \t\"properties\": {\n" +
                "           \"name\": {\n" +
                "              \"type\": \"text\",\n" +
                "              \"analyzer\":\"ik_max_word\",\n" +
                "              \"search_analyzer\":\"ik_smart\"\n" +
                "           },\n" +
                "           \"description\": {\n" +
                "              \"type\": \"text\",\n" +
                "              \"analyzer\":\"ik_max_word\",\n" +
                "              \"search_analyzer\":\"ik_smart\"\n" +
                "           },\n" +
                "           \"studymodel\": {\n" +
                "              \"type\": \"keyword\"\n" +
                "           },\n" +
                "           \"price\": {\n" +
                "              \"type\": \"float\"\n" +
                "           }\n" +
                "        }\n" +
                "}", XContentType.JSON);


        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(JSON.toJSONString(createIndexResponse));

        //释放资源
        restHighLevelClient.close();
    }

    /**
     * 删除索引
     */
    //删除索引库
    @Test
    public void testDeleteIndex() throws IOException {
        //删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("blog2");
        //删除索引
        AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(deleteIndexRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        //删除索引响应结果
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    /**
     * 创建索引 添加文档
     *
     * @throws IOException
     */
    @Test
    public void testAddDocument() throws IOException {


        for (int i = 0; i < 10; i++) {
            //准备json数据
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "spring cloud实战" + i);
            jsonMap.put("description", i + "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud基础入门 3.实战Spring Boot 4.注册中心eureka。");
            jsonMap.put("studymodel", "201001");
            jsonMap.put("price", 5.6f);
            //索引请求对象 索引为 blog1 类型为：doc 文档
            IndexRequest indexRequest = new IndexRequest("blog1").id(i + "");
            //指定索引文档内容
            indexRequest.source(jsonMap);

            //索引响应对象
            IndexResponse index = restHighLevelClient.index(indexRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);

            //获取响应结果
            DocWriteResponse.Result result = index.getResult();
            System.out.println(result);
        }


    }


    /**
     * 创建索引 添加文档
     *
     * @throws IOException
     */
    @Test
    public void testAddDocument1() throws IOException {

            //准备json数据
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "开发" );
            jsonMap.put("description",  "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud基础入门 3.实战Spring Boot 4.注册中心eureka。");
            jsonMap.put("studymodel", "201001");
            jsonMap.put("price", 5.6f);
            //索引请求对象 索引为 blog1 类型为：doc 文档
            IndexRequest indexRequest = new IndexRequest("blog1").id("1001");
            //指定索引文档内容
            indexRequest.source(jsonMap);

            //索引响应对象
            IndexResponse index = restHighLevelClient.index(indexRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);

            //获取响应结果
            DocWriteResponse.Result result = index.getResult();
            System.out.println(result);



    }

    /**
     * 查询文档  根据ID查询
     */
    @Test
    public void getDocumentById() throws IOException {
        GetRequest getRequest = new GetRequest("blog1", "11");

        GetResponse response = restHighLevelClient.get(getRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);

        boolean exists = response.isExists();

        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        System.out.println(sourceAsMap);

    }

    /**
     * 根据ID 更新文档
     */
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("blog1", "doc", "11");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "spring cloud实战333");
//        map.put("price", 5.7f);
        updateRequest.doc(map);

        UpdateResponse update = restHighLevelClient.update(updateRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);

        RestStatus status = update.status();

        System.out.println(status);
    }

    /**
     * 根据ID 删除文档
     */
    @Test
    public void deleteDocument() throws IOException {
        String id = "AXIDNJoC19Z06cvw7_Gv";
        DeleteRequest deleteRequest = new DeleteRequest("blog1", "doc", "11");

        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(delete.status());
    }

    /**
     * 搜索管理  查询所有文档
     */
    @Test
    public void testSearchAll() throws IOException {

        SearchRequest searchRequest = new SearchRequest("blog1");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //source源字段过滤 获取文档的这些字段对应的数据
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
//        searchSourceBuilder.fetchSource(new String[]{"name1", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();

        System.out.println("total=" + totalHits);

        org.elasticsearch.search.SearchHit[] searchHits = hits.getHits();

        for (org.elasticsearch.search.SearchHit searchHit : searchHits) {
            String index = searchHit.getIndex();
            System.out.println("index=" + index);
            String id = searchHit.getId();
            System.out.println("id=" + id);
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            //String articleId = String.valueOf( sourceAsMap.get( "id" ) );
            String title = (String) sourceAsMap.get("name");
            String content = (String) sourceAsMap.get("description");
            //System.out.println("articleId="+articleId);
            System.out.println("title=" + title);
            System.out.println("content=" + content);
        }


    }

    /**
     * 搜索管理  分页查询
     */
    @Test
    public void testSearchByPage() throws IOException {
        //设置要查询的索引 和 type
        SearchRequest searchRequest = new SearchRequest("blog1");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //分页查询，设置起始下标，从0开始
        searchSourceBuilder.from(0);
        //每页显示个数
        searchSourceBuilder.size(2);
        //source源字段过虑
        //searchSourceBuilder.fetchSource(new String[]{"title","id","content"},new String[]{}  );
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }


    /**
     * 搜索管理  Term Query精确查找 ，在搜索时会整体匹配关键字，不再将关键字分词 ，
     * 下面的语句：查询title 包含 spring 的文档
     */
    @Test
    public void testSearchTerm() throws IOException {
        //创建查询，设置索引
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //设置查询条件 模糊搜索：name 属性值中：spring 开头的数据
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring"));
        //source源字段过虑
        //searchSourceBuilder.fetchSource(new String[]{"title","id","content"},new String[]{});
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * 搜索管理 根据ID查询
     */
    @Test
    public void testSearchByID() throws IOException {
        //创建查询，设置索引
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //设置查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = {"11", "12"};
        List<String> stringList = Arrays.asList(ids);
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", stringList));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * 搜索管理 match query 先分词后查找
     */
    @Test
    public void testSearchMatch() throws IOException {
        //创建查询，设置索引
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //设置查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //匹配关键字 分词匹配：spring  开发
        //包含：spring 或者 开发
//        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "spring开发").operator(Operator.OR));
        //同时包含spring和开发关键词
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "spring开发").operator(Operator.AND));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }


    /**
     * 搜索管理 match query 先分词后查找 minimum_should_match  （如果实现三个词至少有两个词匹配如何实现）
     */
    @Test
    public void testSearchMatchMinimnum() throws IOException {
        //创建查询，设置索引
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //设置查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //匹配关键字
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "spring开发").minimumShouldMatch("90%"));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }


    /**
     * 搜索管理 同时搜索多个Field
     */
    @Test
    public void testSearchMultiMatch() throws IOException {
        //创建查询，设置索引
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //设置查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //匹配关键字
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("Boot 开发", "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10);//提升boost 表示权重提升10倍

        searchSourceBuilder.query(multiMatchQueryBuilder);
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * 搜索管理 布尔查询
     */
    @Test
    public void testSearchBoolean() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        //创建搜索源配置对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //multiQuery
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("Boot 开发", "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10);//提升boost 表示权重提升10倍
        //TermQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        // 布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        //设置布尔查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //设置搜索源配置
        searchRequest.source(searchSourceBuilder);
        //搜索
        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }


    /**
     * 搜索管理 过滤器
     */
    @Test
    public void testSearchFilter() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("blog1");
        //设置type
        searchRequest.types("doc");
        // 创建搜索源配置对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //multiQuery 多field查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("Boot 开发", "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10);//提升boost 表示权重提升10倍
        //多Field查询，添加到搜索源配置对象中
        searchSourceBuilder.query(multiMatchQueryBuilder);

        //TermQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        //Term查询，添加到搜索源配置对象中
        searchSourceBuilder.query(termQueryBuilder);

        // 布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(searchSourceBuilder.query());

        //过虑
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(5).lte(100));

        //设置布尔查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //设置搜索源配置
        searchRequest.source(searchSourceBuilder);
        //搜索
        SearchResponse search = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = search.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总条数：" + totalHits);

        for (org.elasticsearch.search.SearchHit searchHit : hits.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * 排序
     *
     * @throws IOException
     */
    @Test
    public void testSort() throws IOException {
        SearchRequest searchRequest = new SearchRequest("blog1");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "price", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //过虑
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        // 排序
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel.keyword").order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        //搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        org.elasticsearch.search.SearchHit[] searchHits = hits.getHits();
        for (org.elasticsearch.search.SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

    /**
     * 高亮显示
     *
     * @throws IOException
     */
    @Test
    public void testHighlight() throws IOException {
        SearchRequest searchRequest = new SearchRequest("blog1");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "price", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        //匹配关键字
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("实战", "name", "description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(searchSourceBuilder.query());
        //过虑
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置前缀
        highlightBuilder.postTags("</tag>");//设置后缀
        // 设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        //highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        org.elasticsearch.search.SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //名称
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField nameField = highlightFields.get("name");
                if (nameField != null) {
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            System.out.println("高亮==" + name);

            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }
}
