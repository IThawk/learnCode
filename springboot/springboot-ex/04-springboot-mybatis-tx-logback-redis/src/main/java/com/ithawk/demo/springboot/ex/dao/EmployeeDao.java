package com.ithawk.demo.springboot.ex.dao;


import com.ithawk.demo.springboot.ex.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao {
    void insertEmployee(Employee employee);

    Integer selectEmployeeCount();

    Employee selectEmployeeById(int id);
}
