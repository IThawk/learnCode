package com.kkb.springmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDispatch(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 请求分发
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void doDispatch(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException ;

}
