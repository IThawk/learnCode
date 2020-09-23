package com.ithawk.mybatis.demo.vip.netty.tomcat.servlet;

import com.ithawk.mybatis.demo.vip.netty.tomcat.http.GPRequest;
import com.ithawk.mybatis.demo.vip.netty.tomcat.http.GPResponse;
import com.ithawk.mybatis.demo.vip.netty.tomcat.http.GPServlet;

public class FirstServlet extends GPServlet {

	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(GPRequest request, GPResponse response) throws Exception {
		response.write("This is First Serlvet");
	}

}
