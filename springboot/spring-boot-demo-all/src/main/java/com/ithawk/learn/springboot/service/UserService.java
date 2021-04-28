package com.ithawk.learn.springboot.service;


import com.ithawk.learn.springboot.common.ExcelMaker;
import com.ithawk.learn.springboot.entity.User;

import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 14:45
 */
public interface UserService extends ExcelMaker<User> {


    int addUser(User user);

    List<User> selectAllUser();
}
