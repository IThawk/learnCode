package com.ithawk.demo.springcloud.provider.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {


    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public boolean postAccessToken(String name)  {
//        UserDto userDto = new UserDto();

        return true;
    }
//    public CommonResult<UserDto> postAccessToken(String name)  {
//        UserDto userDto = new UserDto();
//
//        return CommonResult.success(userDto);
//    }
}
