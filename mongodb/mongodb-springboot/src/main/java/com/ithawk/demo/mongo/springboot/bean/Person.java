package com.ithawk.demo.mongo.springboot.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("人员")
public class Person {

    @ApiModelProperty(example = "LiSi", name = "用户id")
    private String id;

    @ApiModelProperty(example = "李四", name = "用户姓名")
    private String name;

    @ApiModelProperty(example = "20", name = "用户年龄")
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
