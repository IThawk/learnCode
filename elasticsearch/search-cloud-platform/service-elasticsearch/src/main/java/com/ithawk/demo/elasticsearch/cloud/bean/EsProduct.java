package com.ithawk.demo.elasticsearch.cloud.bean;


import com.ithawk.demo.elasticsearch.cloud.core.EsBaseQuery;
import com.ithawk.demo.elasticsearch.cloud.core.EsDocument;
import com.ithawk.demo.elasticsearch.cloud.core.EsFieldType;
import com.ithawk.demo.elasticsearch.cloud.core.EsFiled;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索中的商品信息
 */
@ApiModel("商品信息")
@EsDocument(indexName = "pms", shards = 1, replicas = 0)
@Data
public class EsProduct extends EsBaseQuery  {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    private String productSn;

    @ApiModelProperty(hidden = true)
    private Long brandId;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    private String brandName;

    @ApiModelProperty(hidden = true)
    private Long productCategoryId;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    private String productCategoryName;

    @ApiModelProperty(hidden = true)
    private String pic;

    @ApiModelProperty(value = "名称",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    private String name;

    @ApiModelProperty(value = "主题",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    private String subTitle;


    @ApiModelProperty(value = "关键字",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    private String keywords;

    @ApiModelProperty(hidden = true)
    private BigDecimal price;

    @ApiModelProperty(hidden = true)
    private Integer sale;

    @ApiModelProperty(hidden = true)
    private Integer newStatus;

    @ApiModelProperty(hidden = true)
    private Integer recommandStatus;

    @ApiModelProperty(hidden = true)
    private Integer stock;

    @ApiModelProperty(hidden = true)
    private Integer promotionType;

    @ApiModelProperty(hidden = true)
    private Integer sort;

}
