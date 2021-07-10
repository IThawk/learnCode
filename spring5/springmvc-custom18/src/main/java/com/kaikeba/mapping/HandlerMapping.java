package com.kaikeba.mapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 对于HandlerMapping实现类抽取出来的共性的操作方法
 */
public interface HandlerMapping {

    Object getHandler(HttpServletRequest request);
}
