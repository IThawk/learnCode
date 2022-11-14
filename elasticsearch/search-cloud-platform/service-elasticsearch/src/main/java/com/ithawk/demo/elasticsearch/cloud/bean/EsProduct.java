package com.ithawk.demo.elasticsearch.cloud.bean;


import com.ithawk.demo.elasticsearch.cloud.core.EsBaseQuery;
import com.ithawk.demo.elasticsearch.cloud.core.EsDocument;
import com.ithawk.demo.elasticsearch.cloud.core.EsFieldType;
import com.ithawk.demo.elasticsearch.cloud.core.EsFiled;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索中的商品信息
 */
@ApiModel("商品信息")
@EsDocument(indexName = "pms", shards = 1, replicas = 0)
@Document(indexName = "pms", type = "product", shards = 1, replicas = 0)
@Data
public class EsProduct extends EsBaseQuery  {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(hidden = true)
    @Id
    private Long id;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    private String productSn;

    @ApiModelProperty(hidden = true)
    @Field(type = FieldType.Keyword)
    private Long brandId;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    @Field(type = FieldType.Keyword)
    private String brandName;

    @ApiModelProperty(hidden = true)
    @Field(type = FieldType.Keyword)
    private Long productCategoryId;

    @ApiModelProperty(hidden = true)
    @EsFiled(type = EsFieldType.KEYWORD)
    private String productCategoryName;

    @ApiModelProperty(hidden = true)
    private String pic;

    @ApiModelProperty(value = "名称",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;

    @ApiModelProperty(value = "主题",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String subTitle;


    @ApiModelProperty(value = "关键字",example = "HUAWEI")
    @EsFiled(analyzer = "ik_max_word", type = EsFieldType.TEXT)
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
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

    @Field(type = FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;
}
