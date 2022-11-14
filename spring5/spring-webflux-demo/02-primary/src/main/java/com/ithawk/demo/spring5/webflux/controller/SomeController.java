package com.ithawk.demo.spring5.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j   // lombok日志
@RestController
public class SomeController {

    // 耗时操作
    public String doSome(String msg) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @GetMapping("/common")
    public String commonHandle() {
        log.info("common-start");
        String msg = doSome("common msg");
        log.info("common-end");
        return msg;
    }

    @GetMapping("/mono")
    public Mono<String> monoHandle() {
        log.info("mono-start");
        Mono<String> mono_msg = Mono.fromSupplier(() -> doSome("mono msg"));
        log.info("mono-end");
        return mono_msg;
    }

    @GetMapping("/time")
    public Flux<String> timeHandle(@RequestParam List<String> interests) {
        log.info("flux-start");

        // Flux<String> flux = Flux.fromStream(interests.stream().map(i -> doSome("elem-" + i)));
        Flux<String> flux = Flux.fromStream(interests.stream().map(str -> doSome("abc-" + str + "  ")));

        log.info("flux-end");
        // 将list转为Flux
        return flux;
    }

    @GetMapping(value = "/sse/time", produces = "text/event-stream")
    public Flux<String> timeSSEHandle(@RequestParam List<String> interests) {
        log.info("flux-start");

        // Flux<String> flux = Flux.fromStream(interests.stream().map(i -> doSome("elem-" + i)));
        Flux<String> flux = Flux.fromStream(interests.stream().map(str -> doSome("abc-" + str + "  ")));

        log.info("flux-end");
        // 将list转为Flux
        return flux;
    }

    // SSE，Server Sent Event
    @GetMapping(value="/sse", produces = "text/event-stream")
    public Flux<String> sseHandle() {
        return Flux.just("spring", "mybatis", "hibernate");
    }

}
