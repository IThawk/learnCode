package com.gupao.gpjvm.controller;

import com.gupao.gpjvm.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeapController {
    List<Person> list = new ArrayList<Person>();

    @GetMapping("/heap")
    public String heap() throws Exception {
        while (true) {
            list.add(new Person());
            Thread.sleep(1);
        }
    }
}
