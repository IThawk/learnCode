package com.kkb.springmvc.handler;

import com.kkb.springmvc.handler.iface.SimpleControllerHandler;
import com.kkb.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理添加用户的请求
 * @author 灭霸詹
 *
 */
public class SaveUserHandler implements SimpleControllerHandler {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=utf8");
		response.getWriter().write("SaveUserHandler。。。");
		return null;
	}

}
