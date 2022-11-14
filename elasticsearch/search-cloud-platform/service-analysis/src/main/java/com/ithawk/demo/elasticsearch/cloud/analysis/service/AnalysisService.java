package com.ithawk.demo.elasticsearch.cloud.analysis.service;


import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.ParsedAggregation;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;

import java.util.List;
import java.util.Map;

/**
 * @Class: AnalysisService
 * @Package com.itheima.service
 * @Description: 分析接口
 * @Company: http://www.itheima.com/
 */
public interface AnalysisService {

    //获取搜索热词
    public Map<String, Long> hotwords(CommonEntity commonEntity) throws Exception;

    //指标聚合（OenAPI）
    public Map<Object, Object> metricAgg(CommonEntity commonEntity) throws Exception;



}
