package com.ithawk.demo.spring5.webflux.repository;


import com.ithawk.demo.spring5.webflux.model.UserData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserData, Long> {
    @Query("select * from user_data")
    Flux<UserData> getAllUser();

    @Query("select * from user_data where id = :id")
    Mono<UserData> findByUserId(long id);

    @Query("select * from user_data where first_name = :firstName")
    Mono<UserData> findByFirstName(String firstName);
}