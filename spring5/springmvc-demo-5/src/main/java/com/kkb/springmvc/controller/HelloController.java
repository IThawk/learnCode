package com.kkb.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	/**
	 * 展示页面
	 * @return
	 */
	@RequestMapping("showView")
	public String showView() {
		return "hello";
	}
	
	/**
	 * 返回值上加上ResponseBody注解，表示返回的是一个数据
	 * @return
	 */
	@RequestMapping("showData")
	@ResponseBody
	public String showData() {
		return "showData";
	}
}
