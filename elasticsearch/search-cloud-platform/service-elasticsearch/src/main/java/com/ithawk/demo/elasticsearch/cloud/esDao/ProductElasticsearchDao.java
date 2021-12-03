package com.ithawk.demo.elasticsearch.cloud.esDao;

import com.ithawk.demo.elasticsearch.cloud.bean.EsProduct;
import com.ithawk.demo.elasticsearch.cloud.core.AbstractElasticsearchDao;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


/**
 * 商品ES操作类
 */
@Component
public class ProductElasticsearchDao extends AbstractElasticsearchDao<EsProduct,EsProduct> {

    @PostConstruct
    public void init() {
        clazz = EsProduct.class;
        getBaseModelInfo();
    }

}
