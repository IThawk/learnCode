package com.ithawk.demo.spring.v1.sentinel.sentinelprovider;

import com.ithawk.demo.spring.v1.sentinel.SentinelService;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/10-20:15
 */

@Service//把当前服务发布成dubbo服务
public class SentinelServiceImpl implements SentinelService {

    @Override
    public String sayHello(String txt) {
        return "hello world :" + LocalDateTime.now();
    }
}
