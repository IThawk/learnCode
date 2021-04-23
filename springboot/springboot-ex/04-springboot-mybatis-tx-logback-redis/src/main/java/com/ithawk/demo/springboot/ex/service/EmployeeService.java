package com.ithawk.demo.springboot.ex.service;


import com.ithawk.demo.springboot.ex.bean.Employee;

public interface EmployeeService {

    void addEmployee(Employee employee) throws Exception;

    Employee findEmployeeById(int id);

    Integer findEmployeeCount();
}
