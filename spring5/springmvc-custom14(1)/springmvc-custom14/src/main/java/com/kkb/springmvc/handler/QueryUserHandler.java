package com.kkb.springmvc.handler;

import com.kkb.springmvc.handler.iface.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理查询用户请求
 * @author 灭霸詹
 *
 */
public class QueryUserHandler implements HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=utf8");
		response.getWriter().write("QueryUserHandler。。。");
	}

}
