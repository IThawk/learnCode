package com.ithawk.demo.elasticsearch.cloud.controller;

import com.ithawk.demo.elasticsearch.cloud.commons.enums.ResultEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.TipsEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.result.ResponseData;
import com.ithawk.demo.elasticsearch.cloud.service.ElasticsearchDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     *
     */
    @ApiOperation(value = "全文检索")
    @PostMapping(value = "/mquery")
    public ResponseData matchQuery(@RequestBody CommonEntity commonEntity) {
        ResponseData responseData = new ResponseData();
        SearchResponse searchResponse = null;
        try {
            //远程调用（全文检索）
            searchResponse = elasticsearchDocumentService.matchQuery(commonEntity);
            long aSize = searchResponse.getHits().getTotalHits().value;
            logger.info("数据总数量为>>>" + aSize);
            long cSize = searchResponse.getHits().getHits().length;
            logger.info("本次获取数据量为>>>" + cSize);
            responseData.setResultEnum(searchResponse.getHits().getHits(), ResultEnum.SUCCESS, Integer.valueOf(String.valueOf(aSize)));
            logger.info(TipsEnum.BATCH_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error(TipsEnum.BATCH_GET_DOC_FAIL.getMessage(), e);
            responseData.setResultEnum(ResultEnum.ERROR);
        }


        return responseData;
    }


    /**
     * @Description: 结构化搜索（查询手机在2000-3000元之间、京东物流发货，按照评价进行排序）
     * @Method: termquery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     *
     */
    @ApiOperation(value = "结构化搜索")
    @PostMapping(value = "/tquery")
    public ResponseData termQuery(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
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
            rData.setResultEnum(result.getHits().getHits(), ResultEnum.SUCCESS, Integer.valueOf(String.valueOf(aSize)));
            //日志记录
            logger.info(TipsEnum.BATCH_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.BATCH_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


    /**
     * @Description: 批量新增文档,可自动创建索引、自动创建映射
     * @Method: bulkAddDoc
     * @Param: [indexName, map]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.rest.RestStatus
     *
     */
    @ApiOperation(value = "批量新增文档")
    @PostMapping(value = "/batch")
    public ResponseData bulkAddDoc(@RequestBody CommonEntity commonEntity) {
        //构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || CollectionUtils.isEmpty(commonEntity.getList())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量新增操作返回结果
        RestStatus result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.bulkAddDoc(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, null);
            //日志记录
            logger.info(TipsEnum.BATCH_CREATE_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.info(TipsEnum.BATCH_CREATE_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


    /**
     * @Description 自动补全
     * @Method: suggester
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */
    @ApiOperation(value = "自动补全")
    @PostMapping(value = "/csuggest")
    public ResponseData cSuggest(@RequestBody  CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量查询返回结果
        List<String> result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.cSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, result.size());
            //日志记录
            logger.info(TipsEnum.CSUGGEST_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.CSUGGEST_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


    /**
     * @Description: 拼写纠错
     * @Method: suggester2
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */
    @ApiOperation(value = "拼写纠错")
    @PostMapping(value = "/psuggest")
    public ResponseData pSuggest(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量查询返回结果
        String result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.pSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, null);
            //日志记录
            logger.info(TipsEnum.PSUGGEST_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.PSUGGEST_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


    /**
     * @Description: 搜索推荐（当输入的关键词过多的时候系统进行推荐）
     * @Method: tSuggest
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */
    @ApiOperation(value = "搜索推荐")
    @PostMapping(value = "/tsuggest")
    public ResponseData tSuggest(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName()) || StringUtils.isEmpty(commonEntity.getSuggestFileld()) || StringUtils.isEmpty(commonEntity.getSuggestValue())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量查询返回结果
        String result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = elasticsearchDocumentService.tSuggest(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, null);
            //日志记录
            logger.info(TipsEnum.TSUGGEST_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.TSUGGEST_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


}
