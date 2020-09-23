package com.ithawk.mybatis.demo.demo.service.impl;

import com.ithawk.mybatis.demo.demo.service.IDemoService;
import com.ithawk.mybatis.demo.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService{

	public String get(String name) {
		return "My name is " + name;
	}

}
