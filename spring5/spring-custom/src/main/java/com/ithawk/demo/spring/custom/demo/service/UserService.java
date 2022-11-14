package com.ithawk.demo.spring.custom.demo.service;

import com.ithawk.demo.spring.custom.demo.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {
	List<User> queryUsers(Map<String, Object> param);
}
