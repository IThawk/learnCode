package com.gupaoedu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 让每一个人的职业生涯不留遗憾
 *
 * @author 波波老师【咕泡学院】
 */
@Controller
public class BaseController {

	@GetMapping("/login.html")
	public String loginPage() {
		return "/login.html";
	}

	@GetMapping("/home.html")
	public String home() {
		return "/home.html";
	}

	@GetMapping("/")
	public String basePage() {
		return "/home.html";
	}

	@GetMapping("/error.html")
	public String error() {
		return "/error.html";
	}

}
