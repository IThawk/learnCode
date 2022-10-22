package com.ithawk.demo.code.mgr.config;


import com.ithawk.demo.code.mgr.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object arg2) throws Exception {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        //如果是前端,则不拦截
        if (uri.indexOf("front") > 0) {
            return true;
        }

        /*不拦截静态资源spring mvc4.3以上可以用<mvc:exclude-mapping path=""/>代替*/
        if (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".css") || uri.indexOf(".") > 0) {
            return true;
        }

        //如果是登录页,不拦截
        if (uri.indexOf("login") > 0) {
            return true;
        }
        //获取session中的CURR_USER
        User sessionUserinfo = (User) session.getAttribute("user");
        //如果已经登录,不拦截
        if (sessionUserinfo != null) {
            return true;
        } else {  //如果未登录,跳转到登陆页
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {   /*判断是否为ajax*/
                response.setHeader("REDIRECT", "REDIRECT");  //表示重定向
                response.setHeader("CONTENTPATH", request.getContextPath() + "/login"); //重定向的路径
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); //拒绝访问.
                return false;
            } else {  //如果不是ajax请求则直接跳转到登陆页
                System.out.println("后端拦截器,拦截访问" + uri);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
    }
}
