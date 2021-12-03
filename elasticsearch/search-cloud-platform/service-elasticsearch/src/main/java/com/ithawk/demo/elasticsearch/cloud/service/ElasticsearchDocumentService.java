package com.ithawk.demo.elasticsearch.cloud.service;


import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.List;

/**
 * @Class: ElasticsearchDocumentService
 * @Package com.itheima.service
 * @Description: 文档操作接口
 * @Company: http://www.itheima.com/
 */
public interface ElasticsearchDocumentService {

    //全文检索
    public SearchResponse matchQuery(CommonEntity commonEntity) throws  Exception ;
    //结构化查询
    public SearchResponse termQuery(CommonEntity commonEntity) throws Exception;
    //批量新增文档
    public RestStatus bulkAddDoc(CommonEntity commonEntity) throws Exception;
    //拼写纠错
    public String pSuggest(CommonEntity commonEntity) throws Exception;
    //搜索推荐（当输入的关键词过多的时候系统进行推荐）
    public String tSuggest(CommonEntity commonEntity) throws Exception;
    //自动补全(完成建议)
    public List<String> cSuggest(CommonEntity commonEntity) throws Exception;













}
