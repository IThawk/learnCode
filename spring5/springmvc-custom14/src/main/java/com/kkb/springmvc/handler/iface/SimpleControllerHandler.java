package com.kkb.springmvc.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kkb.springmvc.model.ModelAndView;

/**
 * 模仿Servlet的处理方式，而且可以在response之前，针对响应结果进行拦截处理
 * @author 灭霸詹
 *
 */
public interface SimpleControllerHandler {

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
