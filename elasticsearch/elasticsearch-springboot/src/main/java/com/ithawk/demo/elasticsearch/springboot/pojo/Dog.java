package com.ithawk.demo.elasticsearch.springboot.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel("狗信息")
@Data
public class Dog extends CommonEntity {

    @ApiModelProperty(value = "索引名称",example = "dog")
    //索引名称
    private String indexName ="dog";

    private Long id;

    @ApiModelProperty(value = "名称",example = "泰迪")
    private String name;

    @ApiModelProperty(value = "年龄",example ="1")
    private Integer age;

    @ApiModelProperty(value = "价格",example = "1000.00")
    private Float money;

    private boolean isMarried;

    @ApiModelProperty(value = "批量增加list",example = "[{\"id\":1,\"name\":\"HUAWEI狗\"},{\"id\":2,\"name\":\"XIAOMI狗\"}]")
    //批量增加list
    private List<Map<String, Object>> list;

}
