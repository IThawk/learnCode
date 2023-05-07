package com.xkcoding.orm.excel.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the CMS_PROGRAM_CONTROL database table.
 * CMS_PROGRAM_CONTROL
 */
@Data
public class CmsProgramControl implements Serializable {
    private static final long serialVersionUID = 1L;


    private String programcode;


    private String productcode;


    private String requesttype;

    private String coreaddress;

    private Timestamp createdate;

    private String createuserid;

    private String epolicyaddress;

    private String isstakeholderfix;

    private String owneruserid;

    private String partitionid;


    private String postrule;


    private String prerule;

    private String schemaname;

    private String setid;

    private String structureid;

    private String tenanted;

    private String tenantid;

    private Timestamp updatedate;

    private String updateuserid;

    private String validstatus;


}