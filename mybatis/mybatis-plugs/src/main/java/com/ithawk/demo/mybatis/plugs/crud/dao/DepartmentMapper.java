package com.ithawk.demo.mybatis.plugs.crud.dao;

import com.ithawk.demo.mybatis.plugs.crud.bean.Department;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);

    Department selectByPrimaryKey(Integer deptId);
}