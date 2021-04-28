package com.ithawk.demo.springboot.ex.controller;

import com.ithawk.demo.springboot.ex.vo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class SomeController {

    @RequestMapping("/test/index")
    public String indexHandle(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("welcome", "Hello Thymeleaf World");

        model.addAttribute("student", new Student("张三", 23));

        model.addAttribute("gender", "male");

        model.addAttribute("age", 73);

        List<Student> students = new ArrayList<>();
        students.add(new Student("李四", 24));
        students.add(new Student("王五", 25));
        students.add(new Student("赵六", 26));

        model.addAttribute("students", students);

        Map<String, Student> map = new HashMap<>();
        map.put("stu7", new Student("田七", 27));
        map.put("stu8", new Student("刘八", 28));
        map.put("stu9", new Student("郑九", 29));

        model.addAttribute("map", map);

        model.addAttribute("welcome", "<h2>Thymeleaf, <br>I'm learning.</h2>");

        model.addAttribute("attrName", "score");
        model.addAttribute("attrValue", 99);

        model.addAttribute("photo", "jianshen4.jpg");

        model.addAttribute("elementId", "reddiv");
        model.addAttribute("bgColor", "red");
        model.addAttribute("isClose", false);

        model.addAttribute("school", null);

        List<String> cities = new ArrayList<>();
        model.addAttribute("cities", cities);


        request.setAttribute("req", "reqValue");
        session.setAttribute("ses", "sesValue");
        session.getServletContext().setAttribute("app", "appValue");

        int[] nums = {1,2,3,4,5};
        model.addAttribute("nums", nums);

        model.addAttribute("today", new Date());

        model.addAttribute("cardId", "320406201906023519");


        // 这里的index表示的是Thymeleaf视图页面index.html，但不用写扩展名
        return "index";
    }

}
