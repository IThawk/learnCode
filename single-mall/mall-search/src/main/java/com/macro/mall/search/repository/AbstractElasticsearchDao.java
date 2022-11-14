package com.macro.mall.search.repository;

import com.macro.mall.search.domain.EsProduct;
import com.macro.mall.search.tool.SearchTools;
import io.micrometer.core.instrument.search.Search;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ithawk
 * @projectName single-mall
 * @description: TODO
 * @date 2021/12/117:44
 */
public abstract class AbstractElasticsearchDao<T> {

    protected String index;

    protected Class clazz;

    protected Map<String,String> keyWordMap;

    protected Map<String,String> highLightMap;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    public Page<T> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        //定义返回值
        SearchResponse searchResponse = null;

        //创建查询请求
        SearchRequest searchRequest = new SearchRequest(index);
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        Map<String,Object> commonEntity = new HashMap<>();
        keyWordMap.forEach((f,v)->{
            commonEntity.put(f,keyword);
        });
//        commonEntity.put("name",keyword);
        commonEntity.put("highLight","name");
        //获取客户端查询条件
        SearchTools.getClientConditions(commonEntity, searchSourceBuilder);
        //高亮显示
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.get("highLight").toString()));

        //下标计算
//        int dest = (int) pageable.getOffset();
        int dest = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        searchSourceBuilder.from(dest);
        searchSourceBuilder.size(pageSize);
        //将查询条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //远程查询
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //处理高亮
        SearchTools.setHighResultForCleintUI(searchResponse, commonEntity.get("highLight").toString());
//        SearchHit[] searchHit =searchResponse.getHits().getHits();
//        List<EsProduct> esProducts = new ArrayList<>();
//        for (SearchHit searchHit1:searchHit){
//            EsProduct esProduct =  JSON.parseObject(searchHit1.getSourceAsString(),EsProduct.class);
//            System.out.println(JSON.toJSONString(esProduct));
//            esProducts.add(esProduct);
//        }

        return SearchTools.searchResponseToPage(searchResponse,pageable,clazz);
    }
}
