package com.ithawk.learn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareEmailDetail implements Serializable {

    public static final String USER = "USER";
    public static final String ORM_USER = "ORM_USER";

    private Integer id;

    private String name;

    private String creator;

    private Date createTime;

    private String emailData;

    private String receivers;

    private String content;

    private String subject;

    private Short status;

    private Date lastUpdateTime;

    private String sendType;

    private static final long serialVersionUID = 1L;

}
