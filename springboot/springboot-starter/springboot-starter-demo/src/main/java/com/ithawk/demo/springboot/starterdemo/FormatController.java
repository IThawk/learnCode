package com.ithawk.demo.springboot.starterdemo;

import com.ithawk.demo.springboot.starter.HelloFormatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormatController {

    @Autowired
    HelloFormatTemplate helloFormatTemplate;

    @GetMapping("/format")
    public String format(){
        User user=new User();
        user.setAge(18);
        user.setName("Mic");
        return helloFormatTemplate.doFormat(user);
    }
}
