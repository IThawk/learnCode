package com.ithawk.demo.elasticsearch.cloud.dao;


import com.ithawk.demo.elasticsearch.cloud.bean.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 */
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
