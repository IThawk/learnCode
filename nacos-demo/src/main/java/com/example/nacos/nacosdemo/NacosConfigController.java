package com.example.nacos.nacosdemo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/3-20:44
 */
@NacosPropertySource(dataId = "example",groupId = "DEFAULT_GROUP",autoRefreshed = true)
@RestController
public class NacosConfigController {

    /**
     * 当前的info这个属性，回去nacos-server找到对应的info这个属性
     * 高可用性
     * hello Nacos表示本地属性（降级属性）
     */
    @NacosValue(value = "${info:hello Nacos}",autoRefreshed = true)
    private String info;

    @GetMapping("/get")
    public String get(){
        return info;
    }




}
