package com.ithawk.demo.code.mgr.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用戶
 */
@Data
@Accessors(chain = true)
@TableName("tab_user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    @TableField("user_username")
    private String username;
    @TableField("user_password")
    private String password;
    @TableField("user_real_name")
    private String realName;
    @TableField("user_birthday")
    private Date birthday;
    @TableField("user_sex")
    private Integer userSex = 1;
    @TableField("user_phone")
    private String userPhone;
    @TableField("user_email")
    private String userEmail;
    @TableField("user_status")
    private String userStatus;
    @TableField("user_code")
    private String userCode;
    @TableField("user_admin")
    private String userAdmin;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", birthday=" + birthday +
                ", userSex=" + userSex +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userAdmin='" + userAdmin + '\'' +
                '}';
    }
}
