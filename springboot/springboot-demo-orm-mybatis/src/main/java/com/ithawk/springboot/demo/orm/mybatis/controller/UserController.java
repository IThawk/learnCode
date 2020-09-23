package com.ithawk.springboot.demo.orm.mybatis.controller;

import com.ithawk.springboot.demo.orm.mybatis.entity.User;
import com.ithawk.springboot.demo.orm.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user){
        return userMapper.saveUser(user);
    }
}
