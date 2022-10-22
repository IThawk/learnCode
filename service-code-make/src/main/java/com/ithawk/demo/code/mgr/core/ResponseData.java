package com.ithawk.demo.code.mgr.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
* @Class: ResponseData
* @Package
* @Description: 数据返回封装类
* @Company:
*/
//如果加该注解的字段为null,那么就不序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("数据返回")
public class ResponseData<T> implements Serializable {

    @ApiModelProperty(value = "返回码",example = "200")
    //返回码
    private String code;
    @ApiModelProperty(value = "返回信息",example = "操作成功！")
    //返回信息
    private String desc;
    //返回数据
    //返回的数据
    @ApiModelProperty(value = "返回的数据",example = " ")
    private T data;
    //返回数据总数
    @ApiModelProperty(value = "返回数据总数",example = "1")
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public T getData() {
        return data;
    }

    public ResponseData(T data, ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDecs();
        this.data = data;
    }

    public ResponseData(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDecs();
    }

    public ResponseData(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ResponseData setResultEnum(ResultEnum result) {
        this.code = result.getCode();
        this.desc = result.getDecs();
        return this;
    }

    public ResponseData setResultEnum(T data, ResultEnum resultEnum, Integer count) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDecs();
        this.data = data;
        this.count = count;
        return this;
    }

    public ResponseData(T data, ResultEnum resultEnum, Integer count) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDecs();
        this.data = data;
        this.count = count;
    }

    public ResponseData() {
    }


    public ResponseData setResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
        return this;
     }
}