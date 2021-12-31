package com.ithawk.demo.zookeeper.springboot.controller;

import com.ithawk.demo.zookeeper.springboot.utils.ZookeeperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ithawk
 * @projectName zookeeper
 * @description: TODO
 * @date 2021/12/3114:08
 */
@RestController
@Api("ZookeeperController")
@Slf4j
public class ZookeeperController {

    @Autowired
    private ZookeeperUtils zookeeperUtils;


    @PostMapping("/node")
    @ApiOperation("创建节点")
    public String createZkNode(String path, String value) {
        try {
            zookeeperUtils.createData(path, value, CreateMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }


    @PutMapping("/node")
    @ApiOperation("修改节点")
    public String updateZkNode(String path, String value) {
        try {
            zookeeperUtils.updateData(path, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @DeleteMapping("/node")
    @ApiOperation("删除节点")
    public String deleteZkNode(String path) {
        try {
            zookeeperUtils.deleteData(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @PostMapping("/rootNode")
    @ApiOperation("监控节点")
    public String watchZkNode(String path) {
        try {
            zookeeperUtils.addListenerWithNode(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @PostMapping("/childNode")
    @ApiOperation("监控子节点")
    public String watchZkChildNode(String path) {
        try {
            zookeeperUtils.addListenerWithChild(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
