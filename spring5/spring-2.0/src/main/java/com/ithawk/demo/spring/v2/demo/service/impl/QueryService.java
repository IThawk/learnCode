package com.ithawk.demo.spring.v2.demo.service.impl;

import com.ithawk.demo.spring.v2.demo.service.IQueryService;
import com.ithawk.demo.spring.v2.formework.annotation.GPService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 */
@GPService
@Slf4j
public class QueryService implements IQueryService {

    public void init() {
        System.out.println("init QueryService");
    }

    /**
     * 查询
     */
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
        log.info("这是在业务方法中打印的：" + json);
        return json;
    }

}
