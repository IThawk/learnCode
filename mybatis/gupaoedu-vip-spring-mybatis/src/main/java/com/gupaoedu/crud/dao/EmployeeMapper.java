package com.gupaoedu.crud.dao;

import com.gupaoedu.crud.bean.Employee;
import java.util.HashMap;
import java.util.List;

public interface EmployeeMapper {

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int updateBatch(List<Employee> list);

    int insertSelective(Employee record);

    int batchInsert(List<Employee> list);

    Employee selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    void deleteByList(List<Integer> ids);

    long countByMap(HashMap<String,String> map);

    List<Employee> selectByMap(HashMap<String, Object> map);
}