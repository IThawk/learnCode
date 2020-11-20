package com.macro.mall.controller;


import com.macro.mall.common.api.CommonResult;

import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Controller
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService adminService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam(value = "username") String username,@RequestParam(value = "password")  String password) {
        return adminService.login(username,password);
    }
}
