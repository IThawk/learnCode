package com.ithawk.demo.springboot.ex.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithawk.demo.springboot.ex.dubbo.bean.Student;
import com.ithawk.demo.springboot.ex.dubbo.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class StudentController {

    /**
     * 启动不检查
     */
    @Reference(
            check =false,
            mock = "com.ithawk.demo.springboot.ex.dubbo.mock.StudentServiceMock",
            retries = 2,
            connections = 2

    )
    private StudentService service;

    @PostMapping("/register")
    public String registerHandle(Student student) throws Exception {
        service.saveStudent(student);
        return "/html/welcome.html";
    }

    @GetMapping("/find")
    public Student findHandle(int id) {
        return service.findStudentById(id);
    }

    @GetMapping("/count")
    public Integer countHandle() {
        return service.findStudentsCount();
    }


}
