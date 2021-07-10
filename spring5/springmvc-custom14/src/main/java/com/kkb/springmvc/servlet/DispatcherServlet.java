package com.kkb.springmvc.servlet;

import com.kkb.springmvc.adapter.HttpRequestHandlerAdapter;
import com.kkb.springmvc.adapter.SimpleControllerHandlerAdapter;
import com.kkb.springmvc.adapter.iface.HandlerAdapter;
import com.kkb.springmvc.mapping.BeanNameUrlHandlerMapping;
import com.kkb.springmvc.mapping.SimpleUrlHandlerMapping;
import com.kkb.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends AbstractServlet{
	private static final long serialVersionUID = 1L;

	private List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
	private List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		handlerAdapters.add(new HttpRequestHandlerAdapter());
		handlerAdapters.add(new SimpleControllerHandlerAdapter());
		handlerMappings.add(new BeanNameUrlHandlerMapping());
		handlerMappings.add(new SimpleUrlHandlerMapping());
	}
	
	@Override
	public void doDispatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 根据请求查找处理器
			Object handler = getHandler(request);
			if (handler == null) {
				return ;
			}
			// 根据处理器查找处理器适配器
			HandlerAdapter ha = getHandlerAdapter(handler);
			if (ha == null) {
				return ;
			}
			// 请求处理器适配器执行处理器功能
			ha.handleRequest(handler, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据处理器，找到对应的处理器适配器
	 * @param handler
	 * @return
	 */
	private HandlerAdapter getHandlerAdapter(Object handler) {
		// 根据处理器对象， 从一堆的处理器适配器中，匹配到合适的处理器适配器
		// 策略模式
		if (handlerAdapters != null) {
			for (HandlerAdapter ha : handlerAdapters) {
				if (ha.supports(handler)) {
					return ha;
				}
			}
		}
		
		//如果沒有策略模式
		
//		HandlerAdapter ha;
//		ha = new HttpRequestHandlerAdapter();
//		if(ha.supports(handler)) {
//			return ha;
//		}
//		ha = new SimpleControllerHandlerAdapter();
//		if(ha.supports(handler)) {
//			return ha;
//		}
		return null;
	}

	/**
	 * 根据请求，查找对应的处理器
	 * @param request
	 * @return
	 */
	private Object getHandler(HttpServletRequest request) throws Exception {
		// 需要通过处理器映射器去查找对应的处理器
		if (handlerMappings != null) {
			for (HandlerMapping hm : handlerMappings) {
				Object handler = hm.getHandler(request);
				if (handler != null) {
					return handler;
				}
			}
		}
		return null;
	}

}
