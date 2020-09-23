package com.ithawk.mybatis.demo.crud.dao;

import com.ithawk.mybatis.demo.crud.bean.Department;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);
}