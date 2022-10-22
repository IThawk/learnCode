package com.ithawk.demo.springboot.thymeleaf.mapper;

import com.ithawk.demo.springboot.thymeleaf.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectList();

    void insert(User user);

}
