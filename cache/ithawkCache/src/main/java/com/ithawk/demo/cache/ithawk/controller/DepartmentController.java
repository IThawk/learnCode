package com.ithawk.demo.cache.ithawk.controller;

import com.ithawk.demo.cache.ithawk.bean.Msg;
import com.ithawk.demo.cache.ithawk.constant.ITHawkCache;
import com.ithawk.demo.cache.ithawk.service.DepartmentService;
import com.ithawk.demo.cache.ithawk.bean.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门信息管理
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/depts")
    @ResponseBody
    @ITHawkCache(keyMaker = "depts")
    public Msg getDepts() {
        //查询所有部门信息
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
