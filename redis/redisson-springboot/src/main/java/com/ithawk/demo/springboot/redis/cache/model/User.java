package com.ithawk.demo.springboot.redis.cache.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;
    private String password;
    private int age;
}
