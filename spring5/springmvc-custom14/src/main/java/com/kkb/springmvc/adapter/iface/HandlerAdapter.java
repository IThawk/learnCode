package com.kkb.springmvc.adapter.iface;

import com.kkb.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配器接口
 * @author 灭霸詹
 *
 */
public interface HandlerAdapter {
	
	/**
	 * 完成处理器类和HandlerAdapter之间的适配
	 * @param handler
	 * @return
	 */
	boolean supports(Object handler);
	

	/**
	 * 适配器的执行功能（不同的适配器，执行的处理器是不同的）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	ModelAndView handleRequest(Object handler,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
