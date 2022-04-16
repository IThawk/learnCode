package com.ithawk.demo.spring5.webflux.controller;

import com.ithawk.demo.spring5.webflux.bean.Student;
import com.ithawk.demo.spring5.webflux.repository.StudentRepository;
import com.ithawk.demo.spring5.webflux.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 处理器
 */
@Component
public class StudentHandler {
    @Autowired
    private StudentRepository repository;

    public Mono<ServerResponse> findAllHandler(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.ALL.APPLICATION_JSON_UTF8)
                .body(repository.findAll(), Student.class);
    }

    public Mono<ServerResponse> saveHandler(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);
        return ServerResponse
                .ok()
                .contentType(MediaType.ALL.APPLICATION_JSON_UTF8)
                .body(repository.saveAll(studentMono), Student.class);
    }

    public Mono<ServerResponse> saveValideHandler(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);

        return studentMono.flatMap(stu -> {
            ValidateUtil.valideName(stu.getName());
            return ServerResponse
                    .ok()
                    .contentType(MediaType.ALL.APPLICATION_JSON_UTF8)
                    .body(repository.saveAll(studentMono), Student.class);
        });
    }

    public Mono<ServerResponse> deleteHandler(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                         .flatMap(stu -> repository.delete(stu).then(ServerResponse.ok().build())
                         .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> updateHandler(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Student> studentMono = request.bodyToMono(Student.class);

        return studentMono.flatMap(stu -> {
            // 验证姓名
            ValidateUtil.valideName(stu.getName());
            stu.setId(id);
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(repository.save(stu), Student.class);
        });
    }
}
