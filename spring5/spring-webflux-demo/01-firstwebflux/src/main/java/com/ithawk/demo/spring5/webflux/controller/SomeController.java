package com.ithawk.demo.spring5.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class SomeController {

    @GetMapping("/common")
    public String commonHandle() {
        return "common handler";
    }

    // Mono：包含0个或1个元素的异步序列
    @RequestMapping("/mono")
    public Mono<String> momoHandle() {
        return Mono.just("Hello Mono");
    }

    // Flux：包含0个或多个元素的异步序列
    @RequestMapping("/flux")
    public Flux<String> fluxHandle() {
        return Flux.just("北京", "上海", "广州");
    }

    // 将数组转换为flux
    @RequestMapping("/array")
    public Flux<String> arrayHandle(@RequestParam String[] interests) {
        return Flux.fromArray(interests);
    }

    // 将集合转换为flux
    @RequestMapping("/list")
    public Flux<String> listHandle(@RequestParam List<String> interests) {
        return Flux.fromStream(interests.stream());
    }
}
