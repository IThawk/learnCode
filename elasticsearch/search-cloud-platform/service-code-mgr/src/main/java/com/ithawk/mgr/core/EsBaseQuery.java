package com.ithawk.mgr.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @author ithawk
* Created by CodeGenerator on 2021/12/03.
* @projectName
* @description:
* @date 2021/12/38:54
*/
@Data
public abstract class EsBaseQuery implements Serializable {

    @ApiModelProperty(value = "页码",example = "1")
    //页码
    private int pageNumber;

    @ApiModelProperty(value = "每页数据条数",example = "20")
    //每页数据条数
    private int pageSize;

    @ApiModelProperty(value = "搜索关键字",example = "HUAWEI")
    private String search;


}
