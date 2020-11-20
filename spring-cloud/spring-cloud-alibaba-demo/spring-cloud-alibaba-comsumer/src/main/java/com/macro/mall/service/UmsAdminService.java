package com.macro.mall.service;

import com.macro.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
@FeignClient("spring-cloud-alibaba-provider")
public interface UmsAdminService {

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 调用认证中心返回结果
     */
    @GetMapping("/oauth/token")
    CommonResult login(String username, String password);

}
