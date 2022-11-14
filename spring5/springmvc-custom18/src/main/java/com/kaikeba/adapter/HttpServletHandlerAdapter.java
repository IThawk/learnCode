package com.kaikeba.adapter;

import com.kaikeba.handler.HttpServletHandler;
import com.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门来处理HttpServletHandler处理器类型
 */
public class HttpServletHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HttpServletHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception{
        ((HttpServletHandler)handler).handleRequest(req,resp);
        return null;
    }
}
