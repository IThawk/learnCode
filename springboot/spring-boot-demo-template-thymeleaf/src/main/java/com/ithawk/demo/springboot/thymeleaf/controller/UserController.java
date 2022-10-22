package com.ithawk.demo.springboot.thymeleaf.controller;

import com.ithawk.demo.springboot.thymeleaf.mapper.UserMapper;
import com.ithawk.demo.springboot.thymeleaf.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户页面
 * </p>
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserMapper userMapper;


    @PostMapping("/login")
    public ModelAndView login(User user, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        mv.addObject(user);
        mv.setViewName("redirect:/");

        request.getSession().setAttribute("user", user);
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @GetMapping("/list")
    public ModelAndView list() {
        List<User> list = userMapper.selectList();
        User user = new User();
        user.setId(list.size());
        user.setName("测试" + list.size());
        user.setPassword("测试" + list.size());
        userMapper.insert(user);
        ModelAndView mv = new ModelAndView("user/list");
        mv.addObject("list", list);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add() {

        return new ModelAndView("user/add");
    }

    @PostMapping("/add")
    public ModelAndView add(User user, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("user/list");
        List<User> list = userMapper.selectList();
        user.setId(list.size());
        userMapper.insert(user);
        //跳转到列表
        mv.addObject("list", userMapper.selectList());
        return mv;
    }
}
