package com.ithawk.demo.code.mgr.controller;


import com.ithawk.demo.code.mgr.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 主页
 * </p>
 *
 */
@Controller
@Slf4j
public class IndexController {

	@GetMapping(value = {"", "/"})
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("user");
		if (ObjectUtils.isEmpty(user)|| StringUtils.isEmpty(user.getUsername())) {
			mv.setViewName("redirect:/login");
		} else {
			mv.setViewName("index");
			mv.addObject(user);
		}

		return mv;
	}

	@GetMapping(value = {"", "/my"})
	public ModelAndView indexmy(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

			mv.setViewName("myindex");


		return mv;
	}
}
