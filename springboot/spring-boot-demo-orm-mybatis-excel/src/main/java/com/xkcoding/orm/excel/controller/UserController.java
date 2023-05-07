package com.xkcoding.orm.excel.controller;

import com.xkcoding.orm.excel.entity.ExcelData;
import com.xkcoding.orm.excel.entity.User;
import com.xkcoding.orm.excel.mapper.UserMapper;
import com.xkcoding.orm.excel.service.EasyExcelService;
import com.xkcoding.orm.excel.service.PoiExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EasyExcelService excelService;

    @Autowired
    private PoiExcelService poiExcelService;

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user){
        return userMapper.saveUser(user);
    }

    @GetMapping(value = "test")
    public void test(){
        excelService.importToDatabase();
    }

    @GetMapping(value = "poi")
    public ExcelData testPoi(){

        return poiExcelService.importToDatabase();
    }
}
