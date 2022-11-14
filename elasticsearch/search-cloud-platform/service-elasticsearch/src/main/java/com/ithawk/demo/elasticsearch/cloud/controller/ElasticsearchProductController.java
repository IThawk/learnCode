package com.ithawk.demo.elasticsearch.cloud.controller;

import com.ithawk.demo.elasticsearch.cloud.bean.EsProduct;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.ResultEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.enums.TipsEnum;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.result.ResponseData;
import com.ithawk.demo.elasticsearch.cloud.dao.EsProductDao;
import com.ithawk.demo.elasticsearch.cloud.esDao.EsProductRepository;
import com.ithawk.demo.elasticsearch.cloud.esDao.ProductElasticsearchDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

/**
 * @Class: ElasticsearchProductController
 * @Package
 * @Description: 商品信息搜索
 * @Company:
 */
@Api(tags = "商品信息搜索", value = "ElasticsearchProductController")
@RestController
@RequestMapping("v1/product")
public class ElasticsearchProductController {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchProductController.class);


    @Autowired
    ProductElasticsearchDao productElasticsearchDao;

    @Autowired
    private EsProductRepository productRepository;

    @Autowired
    private EsProductDao productDao;


    /**
     * @Description: 添加商品ES信息
     * @Method:
     * @Param:
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "添加商品ES信息")
    @PostMapping(value = "/importAll")
    public ResponseData importAll() {
        //构造返回数据
        ResponseData rData = new ResponseData();
        try {
            List<EsProduct> esProductList = productDao.getAllEsProductList(null);
            Iterable<EsProduct> esProductIterable = productElasticsearchDao.getBaseEsRepository().saveAll(esProductList);
            Iterator<EsProduct> iterator = esProductIterable.iterator();
            //日志记录
            logger.info(TipsEnum.BATCH_CREATE_DOC_SUCCESS.getMessage());

        } catch (Exception e) {
            //日志记录
            logger.error(TipsEnum.GET_DOC_FAIL.getMessage(), e);
            //构建错误返回信息
            rData.setResultEnum(ResultEnum.ERROR);
        }
        return rData;

    }

    /**
     * @Description: 搜索商品信息
     * @Method:
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return:
     */
    @ApiOperation(value = "搜索商品信息")
    @PostMapping(value = "/search")
    public ResponseData searchProduct(@RequestBody EsProduct esProductQuery) {
        //构造返回数据
        ResponseData rData = new ResponseData();
        try {
            Page<EsProduct> productPage = productElasticsearchDao.search(esProductQuery);

            //构建返回信息通过类型推断自动装箱（多个参数取交集）
            rData.setResultEnum(productPage.getContent(), ResultEnum.SUCCESS, (int) productPage.getTotalElements());
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
