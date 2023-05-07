package com.xkcoding.orm.excel.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the CMS_PROGRAM_SALES database table.
 *  CMS_PROGRAM_SALES
 */
@Data
public class CmsProgramSales implements Serializable {
    private static final long serialVersionUID = 1L;

    private String programcode;


    private String productcode;


    private String salescode;

    private String affiliation;

    private String agentcode;

    private String agreementno;

    private String approvercode;

    private String arbitboardcode;

    private String arbitboardname;

    private String arguesolution;

    private String businessnature;

    private String channelcode;

    private String comcode;

    private Timestamp createdate;

    private String createuserid;

    private String disrate;


    private Date effdate;


    private Date enddate;

    private String handler1code;

    private String handlercode;

    private String identificationcode;

    private String lifeoperatorcode;

    private String makecom;

    private String owneruserid;

    private String partitionid;

    private String remark;

    private String schemaname;

    private String setid;

    private String specialrule;

    private String specialtitlename;

    private String structureid;

    private String tenanted;

    private String tenantid;

    private Timestamp updatedate;

    private String updateuserid;

    private String validstatus;
    
    private String saleamanname;
    
    private String docsalesname;
    
    private String docsalepracticeno;

    private String assignno;//thdp2254
}