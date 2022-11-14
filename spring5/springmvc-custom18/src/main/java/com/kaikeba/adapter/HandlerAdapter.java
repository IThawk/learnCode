package com.kaikeba.adapter;

import com.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DispatcherServlet中用于将各种处理器都适配成统一的HandlerAdapter类型
 */
public interface HandlerAdapter {
    /**
     * 用于判断给定的处理器类型是否是我找个适配器支持的
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp)  throws Exception;
}
