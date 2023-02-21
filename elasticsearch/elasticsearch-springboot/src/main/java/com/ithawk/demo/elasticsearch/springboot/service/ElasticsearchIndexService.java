package com.ithawk.demo.elasticsearch.springboot.service;


import com.ithawk.demo.elasticsearch.springboot.pojo.CommonEntity;

/**
 * @Class: ElasticsearchIndexService
 * @Package com.itheima.service
 * @Description: 索引操作接口
 * @Company: http://www.itheima.com/
 */
public interface ElasticsearchIndexService {
    //新增索引+映射
    boolean addIndexAndMapping(CommonEntity commonEntity) throws Exception;




}
