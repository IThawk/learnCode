package com.ithawk.learn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrmUser implements Serializable {

    public final static List<String> ORM_USER_COLUMN = Arrays.asList("员工名",
        "员工年龄",
        "员工角色");
    private Integer id;

    private String name;

    private String password;

    private String salt;

    private String email;

    private String phoneNumber;

    private Short status;

    private Date createTime;

    private Date lastLoginTime;

    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;

}
