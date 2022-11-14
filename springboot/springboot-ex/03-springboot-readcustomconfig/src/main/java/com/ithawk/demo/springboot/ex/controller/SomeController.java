package com.ithawk.demo.springboot.ex.controller;


import com.ithawk.demo.springboot.ex.dto.Country;
import com.ithawk.demo.springboot.ex.dto.School;
import com.ithawk.demo.springboot.ex.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource(value="classpath:custom.properties", encoding = "utf-8")
public class SomeController {

    @Value("${server.port}")
    private int port;

    @Value("${student.name}")
    private String name;

    @Autowired
    private Student student;

    @Autowired
    private Country country;

    @Autowired
    private School school;


    @GetMapping("/port")
    public String portHandle() {
        return "服务器端口号为：" + port;
    }

    @GetMapping("/name")
    public String nameHandle() {
        return name;
    }

    @GetMapping("/stu")
    public Object studentHandle() {
        return student;
    }

    @GetMapping("/country")
    public Object couHandle() {
        return country;
    }

    @GetMapping("/school")
    public Object schoolHandle() {
        return school;
    }


}
