package com.ithawk.demo.springboot.starterdemo.jmxdemo;

//把需要发布出去的指标信息,通过MB来进行发布
public interface MechineMBean {

    //属性、  操作

    int getCpuCore();

    long getFreeMemory();

    void shutdown();
}
