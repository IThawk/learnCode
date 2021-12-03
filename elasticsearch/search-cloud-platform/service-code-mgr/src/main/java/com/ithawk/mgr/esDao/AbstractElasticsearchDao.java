package com.ithawk.mgr.esDao;

import com.ithawk.mgr.core.EsBaseQuery;
import com.ithawk.mgr.core.CommonEntity;
import com.ithawk.mgr.core.SearchTools;
import com.ithawk.mgr.core.EsDocument;
import com.ithawk.mgr.core.EsFiled;
import com.ithawk.mgr.core.EsFieldType;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
* Created by CodeGenerator on 2021/12/03.
* @author ithawk
*/
public abstract class AbstractElasticsearchDao<T,Q extends EsBaseQuery> {

    protected String index;

    protected Class clazz;

    protected Map<String, String> keyWordMap;

    protected String highLight;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    protected void getBaseModelInfo() {
        if (clazz.isAnnotationPresent(EsDocument.class)) {
            EsDocument document = (EsDocument) clazz.getAnnotation(EsDocument.class);
            index = document.indexName();
            if (keyWordMap == null) {
                keyWordMap = new HashMap<>(clazz.getFields().length);
            }
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(EsFiled.class)) {
                EsFiled field1 = (EsFiled) field.getAnnotation(EsFiled.class);

                if (field1.type() == EsFieldType.TEXT) {
                    keyWordMap.put(field.getName(), "");
                }
                if (field1.highLight()) {
                    highLight = field.getName();
                }

            }
        }

        }
    }

    public Page<T> search(Q query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize());
        //定义返回值
        SearchResponse searchResponse = null;

        //创建查询请求
        SearchRequest searchRequest = new SearchRequest(index);
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        Map<String, Object> commonEntity = new HashMap<>();
        keyWordMap.forEach((f, v) -> {
            commonEntity.put(f, query.getSearch());
        });

        //获取客户端查询条件
        SearchTools.getClientConditions(commonEntity, searchSourceBuilder);
        if (!StringUtils.isEmpty(highLight)) {
            //高亮显示
            searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(highLight));
        }
        //下标计算
        //        int dest = (int) pageable.getOffset();
        int dest = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        searchSourceBuilder.from(dest);
        searchSourceBuilder.size(pageable.getPageSize());
        //将查询条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //远程查询
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(highLight)) {
            //处理高亮
            SearchTools.setHighResultForCleintUI(searchResponse, highLight);
        }

        //        SearchHit[] searchHit =searchResponse.getHits().getHits();
        //        List<EsProduct> esProducts = new ArrayList<>();
        //        for (SearchHit searchHit1:searchHit){
        //            EsProduct esProduct =  JSON.parseObject(searchHit1.getSourceAsString(),EsProduct.class);
        //            System.out.println(JSON.toJSONString(esProduct));
        //            esProducts.add(esProduct);
        //        }

        return SearchTools.searchResponseToPage(searchResponse, pageable, clazz);
    }
}