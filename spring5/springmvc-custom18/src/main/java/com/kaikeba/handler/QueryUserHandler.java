package com.kaikeba.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryUserHandler implements HttpServletHandler{
    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse response) throws Exception{
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("湖人总冠军---QueryUserHandler");
    }
}
