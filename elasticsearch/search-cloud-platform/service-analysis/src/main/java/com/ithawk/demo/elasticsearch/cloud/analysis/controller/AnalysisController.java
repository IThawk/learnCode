package com.ithawk.demo.elasticsearch.cloud.analysis.controller;

import com.ithawk.demo.elasticsearch.cloud.analysis.service.impl.AnalysisServiceImpl;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.ResultEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.TipsEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.result.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Class: AnalysisController
 * @Description: 分析控制器
 */
@RestController
@Api(tags = "分析控制器", value = "AnalysisController")
@RequestMapping("v1/analysis")
public class AnalysisController {
    private static final Logger logger = LoggerFactory
            .getLogger(AnalysisController.class);
    @Autowired
    AnalysisServiceImpl analysisService;


    /**
     * @Description: 指标聚合
     * @Method: metricAgg
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */
    @ApiOperation(value = "指标聚合")
    @PostMapping(value = "/metric/agg")
    public ResponseData metricAgg(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量查询返回结果
        Map<Object, Object> result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = analysisService.metricAgg(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, null);
            //日志记录
            logger.info(TipsEnum.METRICAGG_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.METRICAGG_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }

    /**
     * @Description: 搜索热词
     * @Method: tSuggest
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */
    @ApiOperation(value = "指标聚合")
    @PostMapping(value = "/hotwords")
    public ResponseData hotwords(@RequestBody CommonEntity commonEntity) {
        // 构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //批量查询返回结果
        Map<String, Long> result = null;
        try {
            //通过高阶API调用批量新增操作方法
            result = analysisService.hotwords(commonEntity);
            //通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(result, ResultEnum.SUCCESS, null);
            //日志记录
            logger.info(TipsEnum.HOTWORDS_GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.HOTWORDS_GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


}
