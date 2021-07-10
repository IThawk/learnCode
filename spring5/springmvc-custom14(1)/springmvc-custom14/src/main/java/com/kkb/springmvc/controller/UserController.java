package com.kkb.springmvc.controller;

import com.kkb.springmvc.annotation.Controller;
import com.kkb.springmvc.annotation.RequestMapping;
import com.kkb.springmvc.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用注解方式编写的处理器类
 * 特点：一个类中可以处理多个请求
 * 注意事项：【Controller类不是我们真正意义上的Handler类】
 *  注解方式下真正意义上的处理器类是：HandlerMethod（Controller+@RequestMapping注解的方法）
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/queryUser")
    @ResponseBody
    public Map<String,Object> queryUser(Integer id,String name){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);

        return map;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(){
        return "添加成功！！！";
    }
}
