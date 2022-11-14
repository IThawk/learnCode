package com.ithawk.demo.springboot.ex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping("/first/some")
    public String firstHandle() {
        return "first";
    }

    @GetMapping("/second/some")
    public String secondHandle() {
        return "second";
    }

}
