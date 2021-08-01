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
<<<<<<< HEAD
    @Autowired
    ApplicationUtils applicationUtils;
=======

>>>>>>> aaec40860904e3b1ec6164df914652bc11c958bb

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @GetMapping(value = "all")
    public List<User> addUser() {
<<<<<<< HEAD
        String[]  strings = applicationUtils.getApplicationContext().getBeanDefinitionNames();
=======
        String[]  strings = ApplicationUtils.getApplicationContext().getBeanDefinitionNames();
>>>>>>> aaec40860904e3b1ec6164df914652bc11c958bb
        log.info(JSON.toJSONString(strings,true));
        return userService.getUserList();
    }

}
