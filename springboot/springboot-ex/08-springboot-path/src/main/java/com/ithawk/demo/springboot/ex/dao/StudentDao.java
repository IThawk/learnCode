package com.ithawk.demo.springboot.ex.dao;


import com.ithawk.demo.springboot.ex.bean.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao {
    void insertStudent(Student student);

    Student selectStudentById(int id);

    Integer selectStudentsCount();

}
