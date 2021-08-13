package com.ithawk.demo.cache.ithawk.controller;

import com.ithawk.demo.cache.ithawk.bean.Department;
import com.ithawk.demo.cache.ithawk.constant.ITHawkCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


@Controller
public class DemoController {

    @RequestMapping("/cache")
    @ResponseBody
    @ITHawkCache(keyClass = DemoController.class, valueMethod = "makeITHawkCache")
    public String getDepts(@RequestParam(name = "id") String id) {
        //查询所有部门信息
        return "ddddd";
    }

    @RequestMapping("/cache1")
    @ResponseBody
    @ITHawkCache(keyClass = DemoController.class, valueMethod = "makeITHawkCache")
    public Department getDept(@RequestParam(name = "id") String id) {
        //查询所有部门信息
        return new Department();
    }

    public List<String> makeITHawkCache(String key) {
        return Arrays.asList(key);
    }
}
