package com.ithawk.demo.springboot.ex.controller;


import com.ithawk.demo.springboot.ex.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @Autowired   // byType方式的自动注入
    private SomeService service;

    @RequestMapping("/some/{param}")
    public String someHandle(@PathVariable("param") String param) {
        return service.wrap(param);
    }
}
