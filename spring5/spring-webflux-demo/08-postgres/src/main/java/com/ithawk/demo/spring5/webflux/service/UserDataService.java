package com.ithawk.demo.spring5.webflux.service;

import com.ithawk.demo.spring5.webflux.model.UserData;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author ithawk
 * @projectName spring-webflux-demo
 * @description: TODO
 * @date 2022/4/1915:00
 */
@Service
public class UserDataService {

    @SneakyThrows
    public Mono<UserData> getById(long id){
        Thread.sleep(100000);
        return Mono.just(UserData.builder().firstName("test").password("1233").build());
    }
}
