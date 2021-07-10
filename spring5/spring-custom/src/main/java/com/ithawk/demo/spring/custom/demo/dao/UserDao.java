package com.ithawk.demo.spring.custom.demo.dao;

import com.ithawk.demo.spring.custom.demo.po.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

	List<User> queryUserList(Map<String, Object> param);
}
