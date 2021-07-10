package com.kkb.springmvc.adapter;

import com.kkb.springmvc.adapter.iface.HandlerAdapter;
import com.kkb.springmvc.handler.iface.SimpleControllerHandler;
import com.kkb.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配并处理SimpleControllerHandler处理器类的
 * @author 灭霸詹
 *
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof SimpleControllerHandler);
	}

	@Override
	public ModelAndView handleRequest(Object handler,HttpServletRequest request, HttpServletResponse response) throws Exception {
		return ((SimpleControllerHandler)handler).handleRequest(request, response);
	}

}
