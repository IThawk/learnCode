package com.abc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/info")
public class SomeController {

    @RequestMapping("/header")
    public String headerHandler(HttpServletRequest request) {
        String header = request.getHeader("X-Request-red");
        return "X-Request-red: " + header;
    }

    @RequestMapping("/uri")
    public String uriHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return "uri: " + uri;
    }

    @RequestMapping("/param")
    public String paramHandler(String color) {
        return "color: " + color;
    }

    @RequestMapping("/time")
    public String timeHandler() {
        return "time: " + System.currentTimeMillis();
    }







}
