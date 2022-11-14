package com.kaikeba.handler;

import com.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserHandler implements SimpleControllerHandler{
    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse response) throws Exception{
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("湖人总冠军---SaveUserHandler");
        return null;
    }
}
