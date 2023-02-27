package com.baizhi.test;

import com.baizhi.entity.Fruit;
import com.baizhi.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestFruitService extends ElasticsearchDemoApplicationTests {

    private final FruitService fruitService;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestFruitService(FruitService fruitService, RestHighLevelClient restHighLevelClient) {
        this.fruitService = fruitService;
        this.restHighLevelClient = restHighLevelClient;
    }

    //1.操作 mysql 读取数据
    @Test
    void findAll() {
        fruitService.findAll().forEach(fruit -> {
            System.out.println(fruit.getId() + " " + fruit.getName() + " " + fruit.getPrice() + " " + fruit.getDescription());
        });
    }


    //2.创建索引和映射  RestHighLevClient 1.引入依赖  2.配置创建 resthighlevclient
    @Test
    void createIndexAndMapping() throws IOException {
        //参数 1: 索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("fruit");
        createIndexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"id\":{\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"name\":{\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"price\":{\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"description\":{\n" +
                "        \"type\":\"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }


    //索引文档
    @Test
    void indexDocument() {
        //1.获取所有水果数据
        List<Fruit> fruits = fruitService.findAll();

        //2.将所有水果数据录入到 ES 中  index
        fruits.forEach(fruit -> {
            try {
                //jackson 转 java 对象为 json
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(fruit);
                IndexRequest indexRequest = new IndexRequest("fruit");
                indexRequest.id(fruit.getId().toString()).source(json,XContentType.JSON);
                //index 好处:1.不存在文档直接添加  2.存在文档直接更新
                IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                System.out.println(indexResponse.status());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //查询所有 match_all


    @Test
    void findAllDocumentChangeObject() throws IOException {
        //1.去 ES 中查询所有文档
        SearchRequest searchRequest = new SearchRequest("fruit");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());//查询所有
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //2.获取总条数
        System.out.println("符合条件文档数: "+searchResponse.getHits().getTotalHits().value);
        System.out.println("最大得分: "+searchResponse.getHits().getMaxScore());

        //3.获取内容
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("符合条件文档: "+hit.getSourceAsString());
            //1.第一种解决方案
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            4.讲 json 结果封装为一个 java 对象
//            Fruit fruit = new Fruit();
//            fruit.setId(Integer.valueOf(sourceAsMap.get("id").toString()));
//            fruit.setName(sourceAsMap.get("name").toString());
//            fruit.setPrice(Double.valueOf(sourceAsMap.get("price").toString()));
//            fruit.setDescription(sourceAsMap.get("description").toString());
//            System.out.println(fruit);
            //2.第二种解决放哪  jackson 反序列化
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.readValue(hit.getSourceAsString(), Fruit.class));
        }
    }


    //综合查询 基于关键词查询 term 分页查询 size from  排序 sort 高亮内容 highlighter  返回指定字段

    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("fruit");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery()) //指定查询条件
                .from(0) //起始条数  mysql 一样 默认都是起始位置为0  公式: (currentPage-1)*size
                .size(2) //每页展示记录数
                .sort("price", SortOrder.DESC) //对哪个字段进行排序
                .highlighter(new HighlightBuilder().field("description").requireFieldMatch(false).preTags("<span style='color:red;'>").postTags("</span>")) //高亮处理
                .fetchSource(new String[]{},new String[]{});//指定返回字段  参数 1: 返回结果中只要那些字段   参数 2: 返回结果中不要那些字段
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //总条数  和 最大得分
        System.out.println("符合条件文档的总条数: "+searchResponse.getHits().getTotalHits().value);
        System.out.println("符合条件文档最大得分: "+searchResponse.getHits().getMaxScore());
        //获取符合条件文档
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            //System.out.println("高亮之前原始文档: "+hit.getSourceAsString());//原始 json 格式文档
            //转换对象
            Fruit fruit = new ObjectMapper().readValue(hit.getSourceAsString(), Fruit.class);
            //System.out.println("高亮之前的原始对象:"+fruit);
            //高亮处理
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")){
                String descriptionHighLighter = highlightFields.get("description").fragments()[0].toString();
                fruit.setDescription(descriptionHighLighter);
            }
            System.out.println("高亮之后的对象: "+fruit);

        }
    }
}
