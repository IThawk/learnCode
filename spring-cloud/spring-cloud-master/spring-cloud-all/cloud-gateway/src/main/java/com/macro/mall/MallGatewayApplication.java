package com.macro.mall;

import com.ithawk.demo.springcloud.common.api.CommonResult;
import com.ithawk.demo.springcloud.common.constant.AuthConstant;
import com.macro.mall.handler.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication()
@RestController
public class MallGatewayApplication {

    @Autowired
    AuthService authService;

    public static void main(String[] args) {
        SpringApplication.run(MallGatewayApplication.class, args);
    }


    @ApiOperation("会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username",username);
        params.put("password",password);
        return authService.getAccessToken(params);
    }
}
