package com.ithawk.demo.spring5.webflux.function;

import java.util.Comparator;

// Student比较器
public class StudentComparator implements Comparator<Student> {

    // 比较规则：年龄大者Student对象大
    @Override
    public int compare(Student s1, Student s2) {
        if(s1.getAge() > s2.getAge()) {
            return 1;
        } else if(s1.getAge() < s2.getAge()) {
            return -1;
        }
        return 0;
    }
}
