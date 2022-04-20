package com.ithawk.demo.spring5.webflux.controller;

import com.ithawk.demo.spring5.webflux.repository.UserRepository;
import com.ithawk.demo.spring5.webflux.model.UserData;
import com.ithawk.demo.spring5.webflux.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j   // lombok日志
@RestController
public class SomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/list")
    public Flux<UserData> commonHandle() {

        return userRepository.getAllUser();
    }


    @GetMapping("/{id}")
    public Mono<UserData> commonHandle(@PathVariable long id) {
        
        return userDataService.getById(id).timeout(Duration.ofMillis(6000)).doOnNext(f->{
            System.out.println("超时了");
        });
    }

}
