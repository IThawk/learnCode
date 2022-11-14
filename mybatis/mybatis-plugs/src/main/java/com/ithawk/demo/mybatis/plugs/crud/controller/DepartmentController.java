package com.ithawk.demo.mybatis.plugs.crud.controller;

import com.ithawk.demo.mybatis.plugs.crud.bean.Department;
import com.ithawk.demo.mybatis.plugs.crud.bean.Msg;
import com.ithawk.demo.mybatis.plugs.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门信息管理
 *
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        //查询所有部门信息
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
