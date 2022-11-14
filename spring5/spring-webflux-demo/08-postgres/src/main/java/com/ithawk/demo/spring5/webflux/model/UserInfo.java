package com.ithawk.demo.spring5.webflux.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author ithawk
 * @projectName spring-webflux-demo
 * @description: TODO
 * @date 2022/4/1717:10
 */
@Data
public class UserInfo {
    private Long id;
    @NotBlank(message = "Firstname should not be empty")
    private String firstName;
    @NotBlank(message = "Password should not be empty")
    private String password;

    /**
     * 部门实体
     */
    private UserDataEmail dept;

    private List<UserDataEmail> emailList;
}
