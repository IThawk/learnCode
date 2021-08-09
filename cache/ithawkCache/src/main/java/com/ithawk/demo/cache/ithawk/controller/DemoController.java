package com.ithawk.demo.cache.ithawk.controller;

import com.ithawk.demo.cache.ithawk.bean.Department;
import com.ithawk.demo.cache.ithawk.bean.Msg;
import com.ithawk.demo.cache.ithawk.constate.ITHawkCache;
import com.ithawk.demo.cache.ithawk.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


@Controller
public class DemoController {

    @RequestMapping("/cache")
    @ResponseBody
    @ITHawkCache(keyClass = DemoController.class, valueMethod = "makeITHawkCache")
    public String getDepts() {
        //查询所有部门信息
        return "ddddd";
    }

    public List<String> makeITHawkCache() {
        return Arrays.asList("test") ;
    }
}
