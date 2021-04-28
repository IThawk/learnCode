package com.ithawk.learn.springboot.controller;

import com.ithawk.learn.springboot.entity.User;
import com.ithawk.learn.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @description
 * @author IThawk
 * @date 2020/4/11 13:36
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * @description: //TODO
     * @author IThawk
     * @date 2020/4/11 13:43
     * @param: user
     * @return int
     */
    @PostMapping(value = "post")
    public int addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
