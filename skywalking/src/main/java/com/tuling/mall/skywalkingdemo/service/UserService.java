package com.tuling.mall.skywalkingdemo.service;

import com.tuling.mall.skywalkingdemo.entity.User;

import java.util.List;

/**
 * @author Fox
 */
public interface UserService {

    List<User> list();

    User getById(Integer id);
}
