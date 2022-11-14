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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class StudentController {

    @Autowired
    private StudentService service;

    @RequestMapping("/index")
    public String indexHandle() {
        // 这是一个非杠路径，非杠路径的参照路径是  当前请求路径的资源路径  http://localhost:8080/xxx/test
        // 根据  绝对路径 = 参照路径 + 相对路径
        // 路径解析器解析的绝对路径是   http://localhost:8080/xxx/test/index
        // 这个绝对路径是一个  资源请求路径
        // 按照之前的路径理论所分析出的这个资源请求路径，应该是无法访问到index.html资源的
        // 但实际情况却是可以的。
        // 故，之前所讲路径理论对于一次跳转是没有问题的，若跳转次数超过一次，则有可能会存在问题。
        return "index";
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

    // 这是一个后台路径，后台路径的参照路径是  当前web应用的根  http://localhost:8080/xxx
    // 根据  绝对路径 = 参照路径 + 相对路径
    // 路径解析器解析的绝对路径是   http://localhost:8080/xxx/test/count
    // 这个绝对路径是一个  资源定义路径
    @GetMapping("/count")
    @ResponseBody
    public Integer countHandle() {
        return service.findStudentsCount();
    }

    @GetMapping("/abc")
    public void abcHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 这是一个后台路径，后台路径的参照路径是  当前web应用的根  http://localhost:8080/xxx
        // 根据  绝对路径 = 参照路径 + 相对路径
        // 路径解析器解析的绝对路径是   http://localhost:8080/xxx/html/welcome.html
        // 这个绝对路径是一个  资源请求路径
        // response.sendRedirect("/html/welcome.html");

        // 这是一个非杠路径，非杠路径的参照路径是  当前请求路径的资源路径  http://localhost:8080/xxx/test
        // 根据  绝对路径 = 参照路径 + 相对路径
        // 路径解析器解析的绝对路径是   http://localhost:8080/xxx/test/html/welcome.html
        // 这个绝对路径是一个  资源请求路径
        // response.sendRedirect("html/welcome.html");

        response.sendRedirect(request.getContextPath() + "/html/welcome.html");
    }
}
