package com.ithawk.demo.cache.ithawk.dao;

import com.ithawk.demo.cache.ithawk.bean.Department;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);
}