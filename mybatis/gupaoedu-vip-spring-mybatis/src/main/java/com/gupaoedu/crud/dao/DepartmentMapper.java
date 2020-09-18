package com.gupaoedu.crud.dao;

import com.gupaoedu.crud.bean.Department;
import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);
}