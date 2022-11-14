package com.ithawk.demo.springboot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class SomeController {

    @GetMapping("/some")
    public String someHandle() {
        return "hello spring boot world";
    }
}
