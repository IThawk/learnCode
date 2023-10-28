package com.xkcoding.orm.mybatis.plus.controller;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkcoding.orm.mybatis.plus.entity.User;
import com.xkcoding.orm.mybatis.plus.mapper.UserMapper;
import com.xkcoding.orm.mybatis.plus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.plus.service.impl
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 18:10
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller("myUserController")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/userList")
    @ResponseBody
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/index")
    public String index() {
        return "hello";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello2";
    }
}
