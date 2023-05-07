package com.xkcoding.orm.excel.entity;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CMS_CONTROL_RULE
 * The persistent class for the CMS_CONTROL_RULE database table.
 * 
 */
@Data
public class CmsControlRule implements Serializable {
    private static final long serialVersionUID = 1L;

    private String programcode;
    

    private String productcode;
    

    private long serialno;

    private String controltype;

    private Timestamp createdate;

    private String createuserid;

    private String owneruserid;

    private String paramcode1;

    private String paramcode2;

    private String paramcode3;

    private String partitionid;

    private String remark;

    private String schemaname;

    private String setid;

    private String structureid;

    private String tenanted;

    private String tenantid;

    private Timestamp updatedate;

    private String updateuserid;
    
}