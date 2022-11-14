package com.gupaoedu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 让每一个人的职业生涯不留遗憾
 *
 * @author 波波老师【咕泡学院】
 */
@RestController
public class UserController {

	@PreAuthorize(value = "hasAnyAuthority('ROOT')")
	@RequestMapping("/gp/query1")
	public String query1() {
		return "query1 ...";
	}

	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@RequestMapping("/gp/query2")
	public String query2() {
		return "query2 ...";
	}

}
