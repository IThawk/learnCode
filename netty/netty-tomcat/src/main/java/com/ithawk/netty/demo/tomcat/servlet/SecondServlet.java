package com.ithawk.netty.demo.tomcat.servlet;


import com.ithawk.netty.demo.tomcat.http.Request;
import com.ithawk.netty.demo.tomcat.http.Response;
import com.ithawk.netty.demo.tomcat.http.Servlet;

public class SecondServlet extends Servlet {

	public void doGet(Request request, Response response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(Request request, Response response) throws Exception {
		response.write("This is Second Serlvet");
	}

}
