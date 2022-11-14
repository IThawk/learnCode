package com.ithawk.demo.elasticsearch.cloud.controller;

import com.ithawk.demo.elasticsearch.cloud.commons.enums.ResultEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.TipsEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.result.ResponseData;
import com.ithawk.demo.elasticsearch.cloud.service.ElasticsearchIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Class: ElasticsearchIndexController
 * @Package
 * @Description: 索引操作控制器
 * @Company:
 */
@Api(tags = "索引操作控制器", value = "ElasticsearchIndexController")
@RestController
@RequestMapping("v1/indices")
public class ElasticsearchIndexController {
    private static final Logger logger = LoggerFactory
            .getLogger(ElasticsearchIndexController.class);
    @Autowired
    ElasticsearchIndexService elasticsearchIndexService;


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
    public ResponseData addIndexAndMapping(@RequestBody CommonEntity commonEntity) {
        //构造返回数据
        ResponseData rData = new ResponseData();
        if (StringUtils.isEmpty(commonEntity.getIndexName())) {
            rData.setResultEnum(ResultEnum.PARAM_ISNULL);
            return rData;
        }
        //增加索引是否成功
        boolean isSuccess = false;
        try {
            //通过高阶API调用增加索引方法
            isSuccess = elasticsearchIndexService.addIndexAndMapping(commonEntity );
            //构建返回信息通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(isSuccess, ResultEnum.SUCCESS, 1);
            //日志记录
            logger.info(TipsEnum.CREATE_INDEX_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.CREATE_INDEX_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }


 }
