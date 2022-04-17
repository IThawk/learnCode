package com.ithawk.demo.spring5.webflux.controller;

import com.ithawk.demo.spring5.webflux.repository.UserRepository;
import com.ithawk.demo.spring5.webflux.model.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j   // lombok日志
@RestController
public class SomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/common")
    public Flux<UserData> commonHandle() {

        return userRepository.getAllUser();
    }


}
