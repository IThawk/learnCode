package com.tuling.mall.skywalkingdemo.controller;

import com.tuling.mall.skywalkingdemo.entity.User;
import com.tuling.mall.skywalkingdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author Fox
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/info/{id}")
    public User info(@PathVariable("id") Integer id) {

        if (id == 4) {
            throw new IllegalArgumentException("参数异常");
        }

        try {
            //模拟慢查询
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userService.getById(id);
    }

    @RequestMapping("/list")
    public List<User> list() {

        //TraceContext可以绑定key-value
        TraceContext.putCorrelation("name", "fox");
        Optional<String> op = TraceContext.getCorrelation("name");
        log.info("name = {} ", op.get());
        //获取跟踪的traceId
        String traceId = TraceContext.traceId();
        log.info("traceId = {} ", traceId);

        return userService.list();
    }


}
