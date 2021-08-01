package com.ithawk.demo.springcloud.gateway;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@RestController
public class CloudGatewayApplication9100 {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication9100.class, args);
    }

    // @Bean
    // public IRule loadBalanceRule() {
    //     return new RandomRule();
    // }

    @RequestMapping("login")
    @ResponseBody
    public Object login(HttpServletResponse response) {
        JSONObject userInfo = new JSONObject();

        userInfo.put("username", "xinyues");

        List roles = new ArrayList<>();

        roles.add("Admin");

        roles.add("Dev");

        userInfo.put("roles", roles);//添加角色信息

        response.addHeader("AccountInfo", userInfo.toJSONString());//将信息放入响应的包头

        JSONObject result = new JSONObject();

        result.put("code", 0);

        return result;

    }


}
