package com.tuling.mall.skywalkingdemo.service;

import com.tuling.mall.skywalkingdemo.dao.UserMapper;
import com.tuling.mall.skywalkingdemo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fox
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Trace
    @Tag(key = "list", value = "returnedObj")
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Trace
    @Tags({@Tag(key = "param", value = "arg[0]"),
            @Tag(key = "user", value = "returnedObj")})
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }
}
