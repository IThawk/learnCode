package com.xkcoding.zookeeper.controller;

import com.xkcoding.zookeeper.annotation.LockKeyParam;
import com.xkcoding.zookeeper.annotation.ZooLock;
import com.xkcoding.zookeeper.aspectj.ZooLockAspect;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-31 9:58
 */
@RestController
public class DemoController {

//    @Autowired
//   private CuratorFramework curatorFramework;
    @ZooLock(key = "test")
    @GetMapping(value = "get")
    public String get(){
//        curatorFramework.getData();
        return "test";
    }

}
