package com.ithawk.springboot.demo.orm.mybatis.service.impl;

import com.ithawk.springboot.demo.orm.mybatis.entity.OrmUser;
import com.ithawk.springboot.demo.orm.mybatis.entity.User;
import com.ithawk.springboot.demo.orm.mybatis.mapper.UserMapper;
import com.ithawk.springboot.demo.orm.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.selectAllUser();
    }

    @Override
    public int saveUser(User user) {
        return userMapper.saveUser(user);
    }
}
