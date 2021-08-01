package com.xkcoding.cache.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @package: com.xkcoding.cache.redis.entity
 * @description: 用户实体
 * @author: yangkai.shen
 * @date: Created in 2018/11/15 16:39
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;
    /**
     * 主键id
     */
    private Long id;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 姓名
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}