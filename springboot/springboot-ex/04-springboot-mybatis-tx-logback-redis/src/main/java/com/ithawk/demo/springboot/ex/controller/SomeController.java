package com.ithawk.demo.springboot.ex.controller;


import com.ithawk.demo.springboot.ex.bean.Employee;
import com.ithawk.demo.springboot.ex.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class SomeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public String someHandle(Employee employee) throws Exception {
        employeeService.addEmployee(employee);
        return "page/welcome";
    }

    @RequestMapping("/find")
    @ResponseBody
    public Employee findHandle(int id) {
        return employeeService.findEmployeeById(id);
    }

    @RequestMapping("/count")
    @ResponseBody
    public Integer countHandle() {
        return employeeService.findEmployeeCount();
    }

}
