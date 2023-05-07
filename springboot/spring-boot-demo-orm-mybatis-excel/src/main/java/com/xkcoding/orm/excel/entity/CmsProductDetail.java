package com.xkcoding.orm.excel.entity;

import lombok.Data;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * CMS_PRODUCT_DETAIL
 */
@Data
public class CmsProductDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productcode;

    private long serialno;

    private BigDecimal age;

    private BigDecimal amount;

    private BigDecimal benefitrate;

    private String calculateflag;

    private BigDecimal compensationratio;

    private Timestamp createdate;

    private String createuserid;

    private BigDecimal cumulativelimit;

    private BigDecimal cumulativelimitamount;

    private String currency;

    private BigDecimal deathdislimit;

    private BigDecimal deductible;

    private BigDecimal deductiblerate;

    private BigDecimal eachaccidentlimit;

    private BigDecimal eachaccidentlimitamount;

    private String eachdeathdislded;

    private BigDecimal eachdeathdislimit;

    private BigDecimal eachlawcumlimit;

    private String eachmedcumded;

    private BigDecimal eachmedcumlimit;

    private String eachprocumded;

    private BigDecimal eachprocumlimit;

    private String ismainkind;

    private String itemcode;

    private String kindcode;

    private BigDecimal lawcumlimit;

    private BigDecimal lawcumulativelimitamount;

    private BigDecimal limitamount;

    private String mainkind;

    private BigDecimal medcumlimit;

    private BigDecimal medicalcumulativelimitamount;

    private String owneruserid;

    private String partitionid;

    private BigDecimal percumlimit;

    private BigDecimal premium;

    private BigDecimal propertycumlimit;

    private BigDecimal propertycumulativelimitamount;

    private BigDecimal rate;

    private String schemaname;

    private String setid;

    private String socialsecurity;

    private String structureid;

    private String tenanted;

    private String tenantid;

    private BigDecimal unit;

    private BigDecimal unitamount;

    private Timestamp updatedate;

    private String updateuserid;
    private String coreriskproductcode;

}