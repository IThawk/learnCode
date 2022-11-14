package com.ithawk.demo.springboot.war.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping("/some")
    public String someHandle() {
        return "hello spring boot world";
    }
}
