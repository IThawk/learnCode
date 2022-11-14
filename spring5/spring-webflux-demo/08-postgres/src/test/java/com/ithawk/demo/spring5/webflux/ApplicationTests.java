package com.ithawk.demo.spring5.webflux;

import com.ithawk.demo.spring5.webflux.model.UserData;
import com.ithawk.demo.spring5.webflux.model.UserInfo;
import com.ithawk.demo.spring5.webflux.repository.UserRepository;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
        Flux<UserData> userInfoMono = userRepository.getAllUser();
        System.out.println("fffffff");
                StepVerifier.create(userInfoMono)
                .assertNext(v->{
                    log.info("result: {}",v.toString());
                    assertThat(v.getId()==1).isTrue();
                })
                .verifyComplete();

                Mono<UserData> mono = Mono.just(UserData.builder().firstName("test")
                        .password("123456").build());
                mono.doOnNext(v-> System.out.println(v.toString())).subscribe();
    }

}
