package com.ithawk.demo.zookeeper.springboot.entity;


import java.io.Serializable;

public class Order implements Serializable {
  private Integer id;
  private Integer pid;
  private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
