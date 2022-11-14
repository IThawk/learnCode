package com.ithawk.demo.springboot.ex.controller;


import com.ithawk.demo.springboot.ex.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    @Autowired
    private SomeService service;

    @RequestMapping("/some")
    public String someHandle() {
        return service.send();
    }
}
