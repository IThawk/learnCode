package com.ithawk.orm.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -1840831686851699943L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父 Id
     */
    private String parentId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 乳名
     */
    private String infantName;

    /**
     * 字
     */
    private String courtesyName;

    /**
     * 号
     */
    private String  pseudonym;

    /**
     * 学历
     */
    private String  schooling;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 出生日期
     */
    private Date birthDay;

    /**
     * 死亡日期
     */
    private String deadDay;


    /**
     * 埋葬地点
     */
    private Date deadPlace;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;
}
