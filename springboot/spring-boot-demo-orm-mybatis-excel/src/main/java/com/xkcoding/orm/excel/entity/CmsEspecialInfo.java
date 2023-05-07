package com.xkcoding.orm.excel.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 特约信息表
 *  cms_especial_info
 */
@Data
public class CmsEspecialInfo implements Serializable {
    private static final long serialVersionUID = 3379641115337743751L;

    /** 特约代码 */

    private String especialCode;
    /** 特约代码 */
    private String especialName;
    /** 特约内容 */
    private String context;
    /** 项目编码 */
    private String projectCode;
    /** 新条款/特约代码 */
    private String newEspecialCode;
    /** 标志字段 */
    private String flag;
    /** 备用 */
    private String especialType;
    /** 备用 */
    private String especialProperty;
    /** 备用 */
    private String especialdBased;
    /** 有效状态，0失效/1有效 */
    private String validStatus;


}
