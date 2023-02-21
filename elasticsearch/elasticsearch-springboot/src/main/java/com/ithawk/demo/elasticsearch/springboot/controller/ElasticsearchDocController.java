package com.ithawk.demo.elasticsearch.springboot.controller;


import com.ithawk.demo.elasticsearch.springboot.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.springboot.service.ElasticsearchDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Class: ElasticsearchDocController
 * @Package
 * @Description: 文档操作控制器
 * @Company:
 */
@Api(tags = "文档操作控制器", value = "ElasticsearchDocController")
@RestController
@RequestMapping("v1/docs")
public class ElasticsearchDocController {
    private static final Logger logger = LoggerFactory
            .getLogger(ElasticsearchDocController.class);
    @Autowired
    ElasticsearchDocumentService elasticsearchDocumentService;

    /**
     * @Description: 全文检索
     * @Method: matchQuery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "全文检索")
    @PostMapping(value = "/mquery")
    public Map<String, Object> matchQuery(@RequestBody CommonEntity commonEntity) {
        Map<String, Object> responseData = new HashMap<>();
        SearchResponse searchResponse = null;
        try {
            //远程调用（全文检索）
            searchResponse = elasticsearchDocumentService.matchQuery(commonEntity);
            long aSize = searchResponse.getHits().getTotalHits().value;
            logger.info("数据总数量为>>>" + aSize);
            long cSize = searchResponse.getHits().getHits().length;
            logger.info("本次获取数据量为>>>" + cSize);
            responseData.put("res", "success");
            responseData.put("data", searchResponse.getHits().getHits());
            List<String> objects = Arrays.stream(searchResponse.getHits().getHits())
                    .map(SearchHit::getSourceAsString).collect(Collectors.toList());
            responseData.put("objects", objects);
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            responseData.put("res", "fail");
        }


        return responseData;
    }


    /**
     * @Description: 结构化搜索（查询手机在2000-3000元之间、京东物流发货，按照评价进行排序）
     * @Method: termquery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     */
    @ApiOperation(value = "结构化搜索")
    @PostMapping(value = "/tquery")
    public Map<String, Object> termQuery(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        //批量查询返回结果
        SearchResponse result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.termQuery(commonEntity);
            //查询数量除以每页数量  等于合计分页数量
            long aSize = result.getHits().getTotalHits().value;
            logger.info("总数据量:" + aSize + "条");
            int cSize = result.getHits().getHits().length;
            logger.info("当前获取数据:" + cSize + "条");
            //通过类型推断自动装箱（多个参数取交集）
            responseData.put("res", "success");
            responseData.put("data", result.getHits().getHits());
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            //构建错误返回信息
            responseData.put("res", "fail");
        }
        return responseData;
    }


    /**
     * @Description: 批量新增文档, 可自动创建索引、自动创建映射
     * @Method: bulkAddDoc
     * @Param: [indexName, map]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.rest.RestStatus
     * {
     * "highlight": "name",
     * "indexName": "pms",
     * "map": {
     * "name": "HUAWEI"
     * },
     * "pageNumber": 1,
     * "pageSize": 20,
     * "sortOrder": "ASC",
     * "suggestCount": 2,
     * "suggestFileld": "name",
     * "suggestValue": "name",
     * "list":[
     * {"name":"HUAWEI手机"}
     * ]
     * }
     */
    @ApiOperation(value = "批量新增文档")
    @PostMapping(value = "/batch")
    public Map<String, Object> bulkAddDoc(@RequestBody CommonEntity commonEntity) {
        //构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || CollectionUtils.isEmpty(commonEntity.getList())) {
            responseData.put("res", "fail");
            return responseData;
        }
        //批量新增操作返回结果
        RestStatus result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.bulkAddDoc(commonEntity);
            responseData.put("res", "success");
            responseData.put("data", result);
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            //构建错误返回信息
            responseData.put("res", "fail");
        }
        return responseData;
    }


    /**
     * @Description 自动补全
     * @Method: suggester
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "自动补全")
    @PostMapping(value = "/csuggest")
    public Map<String, Object> cSuggest(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            responseData.put("res", "fail");
            return responseData;
        }
        //批量查询返回结果
        List<String> result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.cSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            responseData.put("res", "success");
            responseData.put("data", result);
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            //构建错误返回信息
            responseData.put("res", "fail");
        }
        return responseData;
    }


    /**
     * @Description: 拼写纠错
     * @Method: suggester2
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "拼写纠错")
    @PostMapping(value = "/psuggest")
    public Map<String, Object> pSuggest(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            responseData.put("res", "fail");
            return responseData;
        }
        //批量查询返回结果
        String result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.pSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）

            responseData.put("res", "success");
            responseData.put("data", result);
        } catch (Exception e) {
            //日志记录
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            //构建错误返回信息
            responseData.put("res", "fail");
        }
        return responseData;
    }


    /**
     * @Description: 搜索推荐（当输入的关键词过多的时候系统进行推荐）
     * @Method: tSuggest
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "搜索推荐")
    @PostMapping(value = "/tsuggest")
    public Map<String, Object> tSuggest(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            responseData.put("res", "fail");
            return responseData;
        }
        //批量查询返回结果
        String result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.tSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            responseData.put("res", "success");
            responseData.put("data", result);
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            //构建错误返回信息
            responseData.put("res", "fail");
        }
        return responseData;
    }


}
