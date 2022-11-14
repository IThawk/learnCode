package ${basePackage}.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.Transient;

/**
* @author ithawk
* Created by ${author} on ${date}.
* @projectName
* @description:
* @date 2021/12/38:54
*/
@Data
public abstract class EsBaseQuery implements Serializable {

    @Transient
    @ApiModelProperty(value = "页码",example = "1")
    //页码
    private int pageNumber;


    @Transient
    @ApiModelProperty(value = "每页数据条数",example = "20")
    //每页数据条数
    private int pageSize;


    @Transient
    @ApiModelProperty(value = "搜索关键字",example = "HUAWEI")
    private String search;


}
