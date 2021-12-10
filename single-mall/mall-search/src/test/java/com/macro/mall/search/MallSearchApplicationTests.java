package com.macro.mall.search;

import com.alibaba.fastjson.JSON;
import com.macro.mall.search.dao.EsProductDao;
import com.macro.mall.search.domain.EsProduct;
import com.macro.mall.search.repository.EsProductRepository;
import com.macro.mall.search.tool.SearchTools;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallSearchApplicationTests {
    @Autowired
    private EsProductDao productDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Test
    public void contextLoads() {
    }
    @Test
    public void testGetAllEsProductList(){
        List<EsProduct> esProductList = productDao.getAllEsProductList(null);
        System.out.print(esProductList);
    }

    @Autowired
    private EsProductRepository productRepository;

    @Test
    public int importAll() {
        List<EsProduct> esProductList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Test
    public void testEsProductMapping(){
//        elasticsearchTemplate.putMapping(EsProduct.class);
//        Map mapping = elasticsearchTemplate.getMapping(EsProduct.class);
//        System.out.println(mapping);

        EsProduct esProduct = elasticsearchTemplate.get("31",EsProduct.class);
        System.out.println(JSON.toJSONString(esProduct));

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //搜索条件
        if(StringUtils.isEmpty("HUAWEI")){
            builder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            builder.withQuery(QueryBuilders.multiMatchQuery("HUAWEI","name","subTitle","keywords"));
        }
        //聚合搜索品牌名称
        builder.addAggregation(AggregationBuilders.terms("brandNames").field("brandName"));
        //集合搜索分类名称
        builder.addAggregation(AggregationBuilders.terms("productCategoryNames").field("productCategoryName"));
        //聚合搜索商品属性，去除type=1的属性
        AbstractAggregationBuilder aggregationBuilder = AggregationBuilders.nested("allAttrValues","attrValueList")
                .subAggregation(AggregationBuilders.filter("productAttrs",QueryBuilders.termQuery("attrValueList.type",1))
                        .subAggregation(AggregationBuilders.terms("attrIds")
                                .field("attrValueList.productAttributeId")
                                .subAggregation(AggregationBuilders.terms("attrValues")
                                        .field("attrValueList.value"))
                                .subAggregation(AggregationBuilders.terms("attrNames")
                                        .field("attrValueList.name"))));
        builder.addAggregation(aggregationBuilder);
        NativeSearchQuery searchQuery = builder.build();
//        return restHighLevelClient.search(searchQuery, response -> {
//            LOGGER.info("DSL:{}",searchQuery.getQuery().toString());
//            return convertProductRelatedInfo(response);//
////            return EsProductRelatedInfo();
//        });
        SearchHits<EsProduct> searchHits =elasticsearchTemplate.search(searchQuery,EsProduct.class);
        System.out.println();
    }

    /*
     * @Description: 获取前端的查询条件
     * @Method: getClientConditions
     * @Param: [commonEntity, searchSourceBuilder]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
    private void getClientConditions(Map<String,Object> commonEntity, SearchSourceBuilder searchSourceBuilder) {
        //循环前端的查询条件
        for (Map.Entry<String, Object> m : commonEntity.entrySet()) {
            if (!StringUtils.isEmpty(m.getKey()) && m.getValue() != null) {
                String key = m.getKey();
                String value = String.valueOf(m.getValue());
                //构造请求体中“query”:{}部分的内容 ,QueryBuilders静态工厂类，方便构造queryBuilder
                //将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
                searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
//                logger.info("search for the keyword:" + value);
            }
        }
    }
    @Test
    public void matchQuery() throws Exception {
        //定义返回值
        SearchResponse searchResponse = null;
        //创建查询请求
        SearchRequest searchRequest = new SearchRequest("pms");
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        Map<String,Object> commonEntity = new HashMap<>();
        commonEntity.put("name","HUAWEI");
        commonEntity.put("highLight","name");
        //获取客户端查询条件
        getClientConditions(commonEntity, searchSourceBuilder);
        //高亮显示
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.get("highLight").toString()));
        //分页
        int pageNumber = 1;
        int pageSize = 20;
        //下标计算
        int dest = (pageNumber - 1) * pageSize;
        searchSourceBuilder.from(dest);
        searchSourceBuilder.size(pageSize);
        //将查询条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //远程查询
        searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //处理高亮
        SearchTools.setHighResultForCleintUI(searchResponse, commonEntity.get("highLight").toString());
        SearchHit[] searchHit =searchResponse.getHits().getHits();
        List<EsProduct> esProducts = new ArrayList<>();
        for (SearchHit searchHit1:searchHit){
            EsProduct esProduct =  JSON.parseObject(searchHit1.getSourceAsString(),EsProduct.class);
            System.out.println(JSON.toJSONString(esProduct));
            esProducts.add(esProduct);
        }
        System.out.println();
    }

}
