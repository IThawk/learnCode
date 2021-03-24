package org.springframework.demo.dao;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.demo.bean.Department;
import org.springframework.stereotype.Component;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByMap(Object o);
}