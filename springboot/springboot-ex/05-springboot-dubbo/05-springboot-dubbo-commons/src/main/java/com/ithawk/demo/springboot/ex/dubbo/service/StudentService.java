package com.ithawk.demo.springboot.ex.dubbo.service;


import com.ithawk.demo.springboot.ex.dubbo.bean.Student;

public interface StudentService {

    void saveStudent(Student student) throws Exception;

    Student findStudentById(int id);

    Integer findStudentsCount();

}
