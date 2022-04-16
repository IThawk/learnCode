package com.ithawk.demo.spring5.webflux.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 参赛学生
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private String school;
    private String gender;
    private int age;
    private double score;
}
