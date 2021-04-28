package com.ithawk.learn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-12 9:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageData {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private Date createTime;
}
