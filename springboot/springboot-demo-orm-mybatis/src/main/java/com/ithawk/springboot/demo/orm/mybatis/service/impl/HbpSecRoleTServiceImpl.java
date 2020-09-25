package com.ithawk.springboot.demo.orm.mybatis.service.impl;

import com.ithawk.springboot.demo.orm.mybatis.entity.HbpSecRoleT;
import com.ithawk.springboot.demo.orm.mybatis.mapper.HbpSecRoleTMapper;
import com.ithawk.springboot.demo.orm.mybatis.query.RoleInfoQuery;
import com.ithawk.springboot.demo.orm.mybatis.service.HbpSecRoleTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class HbpSecRoleTServiceImpl implements HbpSecRoleTService {

    @Autowired
    private HbpSecRoleTMapper hbpSecRoleTMapper;

    @Override
    public List<HbpSecRoleT> getRoleListByRoleInfoQuery(HbpSecRoleT hbpSecRoleT) {
        RoleInfoQuery query = new RoleInfoQuery();
        query.setTenantid(hbpSecRoleT.getTenantid());
        if (StringUtils.isEmpty(hbpSecRoleT.getAuthWay())) {
            query.setAuthWay(hbpSecRoleT.getAuthWay());
        }
        if (StringUtils.isEmpty(hbpSecRoleT.getStatus())) {
            query.setStatus(hbpSecRoleT.getStatus());
        }
        if (StringUtils.isEmpty(hbpSecRoleT.getMainProcessId())) {
            query.setMainProcessId(hbpSecRoleT.getMainProcessId());
        }
        query.setSearch("te");
        return hbpSecRoleTMapper.getRoleListByRoleInfoQuery(query);
    }

    @Override
    public HbpSecRoleT getRoleListByRoleId(String roleId) {
        return hbpSecRoleTMapper.selectByPrimaryKey(roleId);
    }
}
