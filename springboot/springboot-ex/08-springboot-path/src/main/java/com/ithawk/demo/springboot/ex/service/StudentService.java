package com.ithawk.demo.springboot.ex.service;


import com.ithawk.demo.springboot.ex.bean.Student;

public interface StudentService {

    void saveStudent(Student student);

    Student findStudentById(int id);

    Integer findStudentsCount();

}
