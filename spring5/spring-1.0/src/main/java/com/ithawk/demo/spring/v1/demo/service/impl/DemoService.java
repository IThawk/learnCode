package com.ithawk.demo.spring.v1.demo.service.impl;

import com.ithawk.demo.spring.v1.demo.service.IDemoService;
import com.ithawk.demo.spring.v1.mvcframework.annotation.HawkService;

/**
 * 核心业务逻辑
 */
@HawkService
public class DemoService implements IDemoService{

	public String get(String name) {
		return "My name is " + name;
	}

}
