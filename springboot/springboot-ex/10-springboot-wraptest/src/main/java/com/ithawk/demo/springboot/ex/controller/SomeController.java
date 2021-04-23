package com.ithawk.demo.springboot.x.controller;


import com.ithawk.demo.springboot.ex.service.WrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @Autowired
    private WrapService service;

    @GetMapping("/wrap/{param}")
    public String wrapHandle(@PathVariable("param") String word) {
        return service.wrap(word);
    }
}
