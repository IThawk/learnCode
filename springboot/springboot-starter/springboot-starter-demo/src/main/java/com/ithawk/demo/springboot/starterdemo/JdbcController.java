package com.ithawk.demo.springboot.starterdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdbcController {

    //@Qualifier(value = "db1JdbcTemplate") 去定义不同的加载类
    @Autowired
    @Qualifier(value = "db1JdbcTemplate")
    JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier(value = "db2JdbcTemplate")
    JdbcTemplate jdbcTemplate2;


    @GetMapping(value = "/db1")
    public void testjdbc1() {
        jdbcTemplate1.execute("INSERT INTO `user`(`name`, `age`) VALUES ('test', 2);");
    }


    @GetMapping(value = "/db2")
    public void testjdbc2() {
        jdbcTemplate2.execute("INSERT INTO `user`(`name`, `age`) VALUES ('test', 1);");
    }
}
