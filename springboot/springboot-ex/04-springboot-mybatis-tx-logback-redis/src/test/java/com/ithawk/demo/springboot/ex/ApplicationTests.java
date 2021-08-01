package com.ithawk.demo.springboot.ex;

import com.ithawk.demo.springboot.ex.bean.Employee;
import com.ithawk.demo.springboot.ex.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void contextLoads() {
        Employee employee = new Employee();
        employee.setAge(30);
        employee.setName("TEST");
        try {
            employeeService.addEmployee(employee);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}