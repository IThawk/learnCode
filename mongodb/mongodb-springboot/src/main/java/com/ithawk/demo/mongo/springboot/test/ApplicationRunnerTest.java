package com.ithawk.demo.mongo.springboot.test;

import com.ithawk.demo.mongo.springboot.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
@Slf4j
public class ApplicationRunnerTest implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoOps;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        Person p = new Person("Joe", 34);

        // 插入文档
        mongoOps.insert(p);
        log.info("Insert: " + p);

        // 查询文档
        p = mongoOps.findById(p.getId(), Person.class);
        log.info("Found: " + p);

        // 更新文档
        mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
        p = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
        log.info("Updated: " + p);

        // 删除文档
        mongoOps.remove(p);

        // Check that deletion worked
        List<Person> people = mongoOps.findAll(Person.class);
        log.info("Number of people = : " + people.size());

        mongoOps.dropCollection(Person.class);


    }
}