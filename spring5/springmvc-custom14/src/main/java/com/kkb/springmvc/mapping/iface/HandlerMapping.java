package com.kkb.springmvc.mapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器映射器
 * @author 灭霸詹
 *
 */
public interface HandlerMapping {

	Object getHandler(HttpServletRequest request) throws Exception;
}
