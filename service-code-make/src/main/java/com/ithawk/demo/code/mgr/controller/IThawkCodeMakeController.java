package com.ithawk.demo.code.mgr.controller;

import com.ithawk.demo.code.mgr.bean.CodeMakeParam;
import com.ithawk.demo.code.mgr.core.ResponseData;
import com.ithawk.demo.code.mgr.core.ResultEnum;
import com.ithawk.demo.code.mgr.core.TipsEnum;
import com.ithawk.demo.code.mgr.utils.CodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ithawk
 * @projectName elasticsearch
 * @description: TODO
 * @date 2021/12/310:59
 */
@Api(tags = "代码生成器", value = "IThawkCodeMakeController")
@RestController
@RequestMapping("v1/code")
public class IThawkCodeMakeController {
    private static final Logger logger = LoggerFactory.getLogger(IThawkCodeMakeController.class);
    /**
     * @Description: 生成代码
     * @Method:
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "生成代码")
    @PostMapping(value = "/make")
    public ResponseData searchProduct(@RequestBody CodeMakeParam codeMakeParam) {
        //构造返回数据
        ResponseData rData = new ResponseData();

        try {
            CodeGenerator.genCodeByCustomModelName("read_book_pd", "ReadBookPd");
            //日志记录
            logger.info(TipsEnum.GET_DOC_SUCCESS.getMessage());
        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;
    }
}
