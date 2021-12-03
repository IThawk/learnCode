package com.ithawk.demo.elasticsearch.cloud.analysis.service.impl;

import com.ithawk.demo.elasticsearch.cloud.analysis.service.AnalysisService;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.thread.ResponseThreadLocal;
import com.ithawk.demo.elasticsearch.cloud.commons.utils.SearchTools;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.ParsedSingleBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilters;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedHistogram;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Class: AnalysisServiceImpl
 * @Package
 * @Description: 分析实现
 * @Company:
 */
@Service
@RefreshScope
public class AnalysisServiceImpl implements AnalysisService {
    private static final Logger logger = LoggerFactory
            .getLogger(AnalysisServiceImpl.class);
    @Resource
    private RestHighLevelClient client;
    //查询下标（通过每页数量和页数传到后端）
    private static final int START_OFFSET = 0;
    //每页数量
    private static int MAX_COUNT = 50;


    /**
     * @Description: 获取搜索热词
     * @Method: hotwords
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.lang.String>
     */
    public Map<String, Long> hotwords(CommonEntity commonEntity) throws Exception {
        //定义返回数据
        Map<String, Long> map = new LinkedHashMap<String, Long>();
        //执行查询
        SearchResponse result = getSearchResponse(commonEntity);
        //这里的自定义的分组别名（get里面）key当一个的时候为动态获取
        Aggregations aggregations = result.getAggregations();
        String o = aggregations.getAsMap()
                .entrySet().iterator().next()
                .getKey();
        Terms packageAgg = aggregations.get(o);
        for (Terms.Bucket entry : packageAgg.getBuckets()) {
            if (entry.getKey() != null) {
                // key为分组的字段
                String key = entry.getKey().toString();
                // count为每组的条数
                Long count = entry.getDocCount();
                map.put(key, count);
            }
        }

        return map;
    }


    /**
     * @Description: 查询公共调用, 参数模板化
     * @Method: getSearchResponse
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     */
    private SearchResponse getSearchResponse(CommonEntity commonEntity) throws Exception {
        //定义查询请求
        SearchRequest searchRequest = new SearchRequest();
        //指定去哪个索引查询
        searchRequest.indices(commonEntity.getIndexName());
        //构建资源查询构建器，主要用于拼接查询条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //将前端的dsl查询转化为XContentParser
        XContentParser parser = SearchTools.getXContentParser(commonEntity);
        //将parser解析成功查询API
        sourceBuilder.parseXContent(parser);
        //将sourceBuilder赋给searchRequest
        searchRequest.source(sourceBuilder);
        //执行查询
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return response;
    }


    /**
     * @Description: 指标聚合(Open)
     * @Method: metricAgg
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.Map<java.lang.String, java.lang.Long>
     */
    public Map<Object, Object> metricAgg(CommonEntity commonEntity) throws Exception {
        //查询公共调用,将参数模板化
        SearchResponse response = getSearchResponse(commonEntity);
        //定义返回数据
        Map<Object, Object> map = new HashMap<Object, Object>();
        // 此处完全可以返回ParsedAggregation ，不用instance，弊端是返回的数据字段多、get的时候需要写死，下面循环map为的是动态获取key
        Map<String, Aggregation> aggregationMap = response.getAggregations().asMap();
        // 将查询出来的数据放到本地局部线程变量中
        SearchTools.setResponseThreadLocal(response);
        //此处循环一次，目的是动态获取client端传来的【czbk】
        for (Map.Entry<String, Aggregation> m : aggregationMap.entrySet()) {
            //处理指标聚合
            metricResultConverter(map, m);

        }
        //公共数据处理
        mbCommonConverter(map);
        return map;
    }

    /**
     * @Description: 公共数据处理(指标聚合 、 桶聚合)
     * @Method: mbCommonConverter
     * @Param: []
     * @Update:
     * @since: 1.0.0
     * @Return: void
     */
    private void mbCommonConverter(Map<Object, Object> map) {
        if (!CollectionUtils.isEmpty(ResponseThreadLocal.get())) {
            //从线程中取出数据
            map.put("list", ResponseThreadLocal.get());
            //清空本地线程局部变量中的数据，防止内存泄露
            ResponseThreadLocal.clear();
        }

    }


    /**
     * @Description: 指标聚合结果转化器
     * @Method: metricResultConverter
     * @Param: [map, m]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     */
    private void metricResultConverter(Map<Object, Object> map, Map.Entry<String, Aggregation> m) {
        //平均值
        if (m.getValue() instanceof ParsedAvg) {
            map.put("value", ((ParsedAvg) m.getValue()).getValue());
        }
        //最大值
        else if (m.getValue() instanceof ParsedMax) {
            map.put("value", ((ParsedMax) m.getValue()).getValue());
        }
        //最小值
        else if (m.getValue() instanceof ParsedMin) {
            map.put("value", ((ParsedMin) m.getValue()).getValue());
        }
        //求和
        else if (m.getValue() instanceof ParsedSum) {
            map.put("value", ((ParsedSum) m.getValue()).getValue());
        }
        //不重复的值
        else if (m.getValue() instanceof ParsedCardinality) {
            map.put("value", ((ParsedCardinality) m.getValue()).getValue());
        }
        //扩展状态统计
        else if (m.getValue() instanceof ParsedExtendedStats) {
            map.put("count", ((ParsedExtendedStats) m.getValue()).getCount());
            map.put("min", ((ParsedExtendedStats) m.getValue()).getMin());
            map.put("max", ((ParsedExtendedStats) m.getValue()).getMax());
            map.put("avg", ((ParsedExtendedStats) m.getValue()).getAvg());
            map.put("sum", ((ParsedExtendedStats) m.getValue()).getSum());
            map.put("sum_of_squares", ((ParsedExtendedStats) m.getValue()).getSumOfSquares());
            map.put("variance", ((ParsedExtendedStats) m.getValue()).getVariance());
            map.put("std_deviation", ((ParsedExtendedStats) m.getValue()).getStdDeviation());
            map.put("lower", ((ParsedExtendedStats) m.getValue()).getStdDeviationBound(ExtendedStats.Bounds.LOWER));
            map.put("upper", ((ParsedExtendedStats) m.getValue()).getStdDeviationBound(ExtendedStats.Bounds.UPPER));
        }
        //状态统计
        else if (m.getValue() instanceof ParsedStats) {
            map.put("count", ((ParsedStats) m.getValue()).getCount());
            map.put("min", ((ParsedStats) m.getValue()).getMin());
            map.put("max", ((ParsedStats) m.getValue()).getMax());
            map.put("avg", ((ParsedStats) m.getValue()).getAvg());
            map.put("sum", ((ParsedStats) m.getValue()).getSum());
        }

        //百分位等级
        else if (m.getValue() instanceof ParsedTDigestPercentileRanks) {
            for (Iterator<Percentile> iterator = ((ParsedTDigestPercentileRanks) m.getValue()).iterator(); iterator.hasNext(); ) {
                Percentile p = (Percentile) iterator.next();
                map.put(p.getValue(), p.getPercent());
            }
        }
        //百分位度量
        else if (m.getValue() instanceof ParsedTDigestPercentiles) {
            for (Iterator<Percentile> iterator = ((ParsedTDigestPercentiles) m.getValue()).iterator(); iterator.hasNext(); ) {
                Percentile p = (Percentile) iterator.next();
                map.put(p.getPercent(), p.getValue());

            }
        }


    }


}
