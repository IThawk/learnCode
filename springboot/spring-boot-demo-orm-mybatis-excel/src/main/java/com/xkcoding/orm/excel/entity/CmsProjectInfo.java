package com.xkcoding.orm.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.xkcoding.orm.excel.PoiExecl;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the CMS_PROJECT_INFO database table.
 *  CMS_PROJECT_INFO
 */
@Data
@PoiExecl(title = "项目信息")
public class CmsProjectInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("项目编码")
    @PoiExecl(title = "项目编码")
    private String projectcode;

    private String affiliation;

    private String contacttel;

    private Timestamp createdate;

    private String createuserid;

    @ExcelProperty("数据来源")
    @PoiExecl(title = "数据来源")
    private String datasource;

    private String datasourcename;

    private String linkername;

    private String organizationid;

    private String owneruserid;


    private String partitionid;

    @ExcelProperty("项目名称")
    @PoiExecl(title = "项目名称")
    private String projectname;

    private String remark;

    private String schemaname;

    private String setid;

    private String structureid;

    @PoiExecl(title = "数据源")
    private String systemcode;

    private String tenanted;

    private String tenantid;

    private Timestamp updatedate;

    private String updateuserid;

    @ExcelProperty("密码")
    @PoiExecl(title = "密码")
    private String useracct;

    private String useraddress;

    private String userdesc;

    @ExcelProperty("用户名")
    @PoiExecl(title = "用户名")
    private String username;

    private String userpw;

    private String validstatus;


}