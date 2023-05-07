package com.xkcoding.orm.excel.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the CMS_PROGRAM_INFO database table.
 * CMS_PROGRAM_INFO
 */
@Data
public class CmsProgramInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String programcode;


    private String productcode;


    private String salescode;

    private Timestamp createdate;

    private String createuserid;

    private String owneruserid;

    private String partitionid;

    private String programdesc;

    private String projectcode;

    private String remark;

    private String schemaname;

    private String setid;

    private String structureid;

    private String tenanted;

    private String tenantid;

    private Timestamp updatedate;

    private String updateuserid;

    private String validstatus;
    //总部需求部门编号
    private String businesscode;
    //互联网业务类型
    private String internetbusinessflagfield;

    private Timestamp stateflipdate;

}