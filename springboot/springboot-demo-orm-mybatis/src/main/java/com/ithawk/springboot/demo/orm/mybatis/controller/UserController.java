package com.ithawk.springboot.demo.orm.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.ithawk.springboot.demo.orm.mybatis.entity.User;
import com.ithawk.springboot.demo.orm.mybatis.service.UserService;
import com.ithawk.springboot.demo.orm.mybatis.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    ApplicationUtils applicationUtils;

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @GetMapping(value = "all")
    public List<User> addUser() {
        String[]  strings = applicationUtils.getApplicationContext().getBeanDefinitionNames();
        log.info(JSON.toJSONString(strings,true));
        return userService.getUserList();
    }

}
