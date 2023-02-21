package com.ithawk.demo.elasticsearch.springboot.controller;

import com.ithawk.demo.elasticsearch.springboot.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.springboot.service.ElasticsearchIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String,Object> addIndexAndMapping(@RequestBody CommonEntity commonEntity) {
        //构造返回数据
        Map<String,Object> responseData = new HashMap<>();
        if (StringUtils.isEmpty(commonEntity.getIndexName())) {
            responseData.put("res","fail");
            return responseData;
        }
        //增加索引是否成功
        boolean isSuccess = false;
        try {
            //通过高阶API调用增加索引方法
            isSuccess = elasticsearchIndexService.addIndexAndMapping(commonEntity );
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


 }
