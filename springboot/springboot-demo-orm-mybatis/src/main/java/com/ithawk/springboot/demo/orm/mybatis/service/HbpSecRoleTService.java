package com.ithawk.springboot.demo.orm.mybatis.service;

import com.ithawk.springboot.demo.orm.mybatis.entity.HbpSecRoleT;

import java.util.List;

public interface HbpSecRoleTService {

    List<HbpSecRoleT> getRoleListByRoleInfoQuery(HbpSecRoleT hbpSecRoleT);

    HbpSecRoleT getRoleListByRoleId(String roleId);
}
