package com.ithawk.demo.spring5.webflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Controller
public class SomeController {

    // 向客户端发送普通响应
    @RequestMapping("/common")
    public void commonHandle(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        for (int i=0; i<10; i++) {
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 向客户端发送默认事件的SSE响应
    @RequestMapping("/sse/default")
    public void defaultHandle(HttpServletResponse response) throws IOException {
        // 按照SSE规范进行设置
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        // 下面的代码没有做修改
        PrintWriter out = response.getWriter();
        for (int i=0; i<10; i++) {
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/default")
    public String defaultsseHandle() {
        return "/defaultsse";
    }

    // 向客户端发送自定义事件的SSE响应
    @RequestMapping("/sse/custom")
    public void customHandle(HttpServletResponse response) throws IOException {
        // 按照SSE规范进行设置
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        for (int i=0; i<10; i++) {
            // 设置自定义事件的名称
            out.print("event:china\n");
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/custom")
    public String customsseHandle() {
        return "/customsse";
    }

}
