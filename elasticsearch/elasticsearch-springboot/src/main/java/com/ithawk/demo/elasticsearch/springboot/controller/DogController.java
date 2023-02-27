package com.ithawk.demo.elasticsearch.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.elasticsearch.springboot.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.springboot.pojo.Dog;
import com.ithawk.demo.elasticsearch.springboot.service.ElasticsearchDocumentService;
import com.ithawk.demo.elasticsearch.springboot.service.ElasticsearchIndexService;
import com.ithawk.demo.elasticsearch.springboot.utils.JsonUtils;
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
 * @Class: ElasticsearchIndexController
 * @Package
 * @Description: 索引操作控制器
 * @Company:
 */
@Api(tags = "dog", value = "DogController")
@RestController
@RequestMapping("v1/dog")
public class DogController {
    private static final Logger logger = LoggerFactory
            .getLogger(DogController.class);
    @Autowired
    ElasticsearchIndexService elasticsearchIndexService;
    @Autowired
    ElasticsearchDocumentService elasticsearchDocumentService;

    /**
     * @Description: 新增索引、映射
     * @Method: addIndex
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: com.itheima.commons.result.ResponseData
     *
     */
    @ApiOperation(value = "新增索引")
    @PostMapping(value = "/add")
    public Map<String,Object> addIndexAndMapping(@RequestBody Dog dog) {
        //构造返回数据
        Map<String,Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(dog.getIndexName())) {
            responseData.put("res","fail");
            return responseData;
        }
        //增加索引是否成功
        boolean isSuccess = false;
        try {
            //通过高阶API调用增加索引方法
            isSuccess = elasticsearchIndexService.addIndexAndMapping(dog);
            //构建返回信息通过类型推断自动装箱（多个参数取交集）
            responseData.put("res","success");
            responseData.put("data",isSuccess);
        } catch (Exception e) {
            //日志记录
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            responseData.put("res","fail");
        }
        return responseData;
    }

    @ApiOperation(value = "批量新增文档")
    @PostMapping(value = "/batch")
    public Map<String, Object> bulkAddDoc(@RequestBody Dog commonEntity) {
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
     * @Description: 全文检索
     * @Method: matchQuery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "全文检索")
    @PostMapping(value = "/mquery")
    public Map<String, Object> matchQuery(@RequestBody Dog commonEntity) {
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
            List<Dog> objects = JsonUtils.searchResponseHitsToList(searchResponse,Dog.class);
            responseData.put("objects", objects);
        } catch (Exception e) {
            logger.error("error :{}", ExceptionUtils.getStackTrace(e));
            responseData.put("res", "fail");
        }


        return responseData;
    }
 }
