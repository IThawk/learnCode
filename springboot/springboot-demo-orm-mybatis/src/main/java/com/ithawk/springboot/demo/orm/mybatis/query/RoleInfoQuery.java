package com.ithawk.springboot.demo.orm.mybatis.query;

import com.ithawk.springboot.demo.orm.mybatis.entity.HbpSecRoleT;

import java.util.Date;

public class RoleInfoQuery extends HbpSecRoleT {


    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
