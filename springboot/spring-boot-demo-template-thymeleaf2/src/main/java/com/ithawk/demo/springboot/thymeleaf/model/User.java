package com.ithawk.demo.springboot.thymeleaf.model;

import lombok.Data;

/**
 * <p>
 * 用户 model
 * </p>
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String password;
}
