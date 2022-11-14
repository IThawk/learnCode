package com.ithawk.demo.springboot.ex.bean;


import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private Integer id;
    private String name;
    private int age;
}
