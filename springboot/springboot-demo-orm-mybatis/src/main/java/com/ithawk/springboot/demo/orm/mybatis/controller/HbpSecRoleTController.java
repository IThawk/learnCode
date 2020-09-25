package com.ithawk.springboot.demo.orm.mybatis.controller;

import com.ithawk.springboot.demo.orm.mybatis.entity.HbpSecRoleT;
import com.ithawk.springboot.demo.orm.mybatis.query.RoleInfoQuery;
import com.ithawk.springboot.demo.orm.mybatis.service.HbpSecRoleTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class HbpSecRoleTController {

    @Autowired
    private HbpSecRoleTService hbpSecRoleTService;

    @PostMapping(value = "getRoleList")
    public List<HbpSecRoleT> getList(@RequestBody HbpSecRoleT roleInfoQuery) {
        return hbpSecRoleTService.getRoleListByRoleInfoQuery(roleInfoQuery);
    }
}