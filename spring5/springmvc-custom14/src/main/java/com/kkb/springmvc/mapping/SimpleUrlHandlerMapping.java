package com.kkb.springmvc.mapping;

import com.kkb.springmvc.handler.SaveUserHandler;
import com.kkb.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 将处理器类配置到xml中的bean标签
 * @author 灭霸詹
 *
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {
	
	/**
	 * 请求和处理器类的映射集合
	 */
	private Map<String, Object> urlHandlers = new HashMap<String, Object>();

	public SimpleUrlHandlerMapping() {
		urlHandlers.put("/saveUser", new SaveUserHandler());
	}
	
	@Override
	public Object getHandler(HttpServletRequest request) throws Exception {
		String uri = request.getRequestURI();
		return urlHandlers.get(uri);
	}

}
