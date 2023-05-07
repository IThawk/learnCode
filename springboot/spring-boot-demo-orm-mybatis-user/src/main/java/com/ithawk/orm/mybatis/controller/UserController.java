package com.ithawk.orm.mybatis.controller;

import com.ithawk.orm.mybatis.entity.User;
import com.ithawk.orm.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user) {
        return userMapper.saveUser(user);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/")
    public String index() {
//        PageInfo<User> page = userService.findByPage(pageNum, pageSize, vo);
//        model.addAttribute("page", page);
        return "index";
    }
    /**
     * 分页查询
     */
    @RequestMapping("/list")
    public String page() {
//        PageInfo<User> page = userService.findByPage(pageNum, pageSize, vo);
//        model.addAttribute("page", page);
        return "user_list";
    }
    @RequestMapping("/login")
    public String login() {
//        PageInfo<User> page = userService.findByPage(pageNum, pageSize, vo);
//        model.addAttribute("page", page);
        return "login";
    }

    /**
     * 分页查询
     */
    @GetMapping("/uesrList")
    @ResponseBody
    public List<User> uesrList() {
       log.info("11111111111111111111");
        User user = new User();
        user.setId("121212121");
        return Arrays.asList();
    }
}
