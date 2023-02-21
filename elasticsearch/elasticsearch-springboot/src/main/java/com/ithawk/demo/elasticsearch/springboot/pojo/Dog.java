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
    private String indexName;

    private Long id;

    private String name;

    private Integer age;

    private Float money;

    private boolean isMarried;

    @ApiModelProperty(value = "批量增加list",example = "[{\"id\":1,\"name\":\"HUAWEI狗\"},{\"id\":2,\"name\":\"XIAOMI狗\"}]")
    //批量增加list
    private List<Map<String, Object>> list;

}
