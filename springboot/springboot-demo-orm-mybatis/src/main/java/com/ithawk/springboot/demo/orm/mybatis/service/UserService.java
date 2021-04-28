package com.ithawk.springboot.demo.orm.mybatis.service;

import com.ithawk.springboot.demo.orm.mybatis.entity.OrmUser;
import com.ithawk.springboot.demo.orm.mybatis.entity.User;


import java.util.List;

public interface UserService {

    List<User> getUserList();

    int saveUser(User user);


}
