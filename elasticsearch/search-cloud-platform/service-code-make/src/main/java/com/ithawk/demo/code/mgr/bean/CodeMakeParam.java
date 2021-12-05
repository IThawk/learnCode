package com.ithawk.demo.code.mgr.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ithawk
 * @projectName elasticsearch
 * @description: TODO
 * @date 2021/12/40:16
 */
@Data
@ApiModel
public class CodeMakeParam implements Serializable {

    @ApiModelProperty(value = "数据库IP", name = "databaseHost",example = "127.0.0.1")
    String databaseHost;

    @ApiModelProperty(value = "", name = "databasePort",example = "3306")
    String databasePort;

    @ApiModelProperty(value = "数据库", name = "database",example = "test")
    String database;

    @ApiModelProperty(value = "", name = "projectName",example = "代码示例")
    String projectName;

    @ApiModelProperty(value = "", name = "groupId",example = "com.ithawk.make")
    String groupId;

    @ApiModelProperty(value = "", name = "artifactId",example = "base")
    String artifactId;

    @ApiModelProperty(value = "", name = "version",example = "1.0.0")
    String version;
}
