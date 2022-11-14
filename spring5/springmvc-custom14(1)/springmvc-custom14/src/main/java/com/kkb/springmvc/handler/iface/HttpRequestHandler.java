package com.kkb.springmvc.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿Servlet的处理方式
 * @author 灭霸詹
 *
 */
public interface HttpRequestHandler {

	public void handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
