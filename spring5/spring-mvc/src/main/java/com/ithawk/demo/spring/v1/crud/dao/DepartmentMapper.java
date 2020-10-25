package com.ithawk.demo.spring.v1.crud.dao;

import com.ithawk.demo.spring.v1.crud.bean.Department;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);
}