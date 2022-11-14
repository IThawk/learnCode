package com.ithawk.demo.spring.v1.demo.mvc.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ithawk.demo.spring.v1.demo.service.IDemoService;
import com.ithawk.demo.spring.v1.mvcframework.annotation.HawkAutowired;
import com.ithawk.demo.spring.v1.mvcframework.annotation.HawkController;
import com.ithawk.demo.spring.v1.mvcframework.annotation.HawkRequestMapping;
import com.ithawk.demo.spring.v1.mvcframework.annotation.HawkRequestParam;


//虽然，用法一样，但是没有功能
@HawkController
@HawkRequestMapping("/demo")
public class DemoAction {

  	@HawkAutowired
    private IDemoService demoService;

	/**
	 * 请求实例：
	 * http://localhost:9090/spring_1_0_war_exploded/demo/query?name=1
	 * @param req
	 * @param resp
	 * @param name
	 */
	@HawkRequestMapping("/query.*")
	public void query(HttpServletRequest req, HttpServletResponse resp,
					  @HawkRequestParam("name") String name){
//		String result = demoService.get(name);
		String result = "My name is " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@HawkRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
					@HawkRequestParam("a") Integer a, @HawkRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@HawkRequestMapping("/sub")
	public void add(HttpServletRequest req, HttpServletResponse resp,
					@HawkRequestParam("a") Double a, @HawkRequestParam("b") Double b){
		try {
			resp.getWriter().write(a + "-" + b + "=" + (a - b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@HawkRequestMapping("/remove")
	public String  remove(@HawkRequestParam("id") Integer id){
		return "" + id;
	}

}
