package com.ithawk.demo.springboot.ex.controller;

import com.ithawk.demo.springboot.ex.bean.Student;
import com.ithawk.demo.springboot.ex.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class StudentController {

    @Autowired
    private StudentService service;

    @RequestMapping("/index")
    public String indexHandle() {
        return "/index";
    }

    @PostMapping("/register")
    public String registerHandle(Student student, Model model) {
        service.saveStudent(student);
        model.addAttribute("student", student);
        return "/html/welcome";
    }

    @GetMapping("/find")
    public String findHandle(int id, Model model) {
        Student student = service.findStudentById(id);
        model.addAttribute("student", student);
        return "/html/welcome";
    }

    @GetMapping("/count")
    @ResponseBody
    public Integer countHandle() {
        return service.findStudentsCount();
    }


}
