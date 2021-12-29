package com.ithawk.demo.mongo.springboot.controller;

import com.ithawk.demo.mongo.springboot.bean.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author ithawk
 * @projectName mongodb
 * @description:
 * @date 2021/12/2911:42
 */
@RestController
@Api("MongodbController")
@Slf4j
public class MongodbController {

    @Autowired
    private MongoTemplate mongoOps;

    @PostMapping("/person")
    @ApiOperation("添加用户")
    public String insert(@RequestBody Person person) {
        // 插入文档
        mongoOps.insert(person);
        log.info("Insert: " + person);
        return "OK";
    }

    @GetMapping("/person")
    @ApiOperation("ID查询用户")
    public Person getById(@RequestParam("id") String id) {
        // 查询文档
        Person p = mongoOps.findById(id, Person.class);
        log.info("Found: " + p);
        return p;
    }

    @PutMapping("/person")
    @ApiOperation("根据名称修改用户")
    public String updatePerson(@RequestBody Person person) {

        // 更新文档
        mongoOps.updateFirst(query(where("name").is(person.getName())), update("age", 35), Person.class);
        log.info("Updated: " + person);
        person = mongoOps.findOne(query(where("name").is(person.getName())), Person.class);

        log.info("Found: " + person);
        return "OK";
    }

    @DeleteMapping("/person")
    @ApiOperation("删除用户")
    public String deletePerson(@RequestBody Person person) {

        // 删除文档
        mongoOps.remove(person);
        return "OK";
    }

    @GetMapping("/all")
    @ApiOperation("查询所有用户")
    public List<Person> queryAllPerson() {
        // Check that deletion worked
        List<Person> people = mongoOps.findAll(Person.class);
        log.info("Number of people = : " + people.size());

//        mongoOps.dropCollection(Person.class);
        return people;
    }

}
