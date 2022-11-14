package com.ithawk.demo.elasticsearch.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.elasticsearch.springboot.config.ElasticSearchConfig;
import com.ithawk.demo.elasticsearch.springboot.pojo.Car;
import com.ithawk.demo.elasticsearch.springboot.pojo.Stu;
import com.ithawk.demo.elasticsearch.springboot.utils.JsonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/high")
public class HighLevelController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RequestMapping("hello")
    public Object hello() {
        return "OK";
    }

    /**
     * 创建索引
     */
    @RequestMapping("createIndex")
    public Object createIndex() throws IOException {

        IndexRequest indexRequest = new IndexRequest("car");
        indexRequest.id("1");
        Car car = new Car();
        car.setUserName("test");
        indexRequest.source(JSON.toJSONString(car), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(indexResponse);
        return "OK";
    }


}
