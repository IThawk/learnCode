package com.ithawk.orm.mybatis.constant;

public enum EventType {
    BRITH("brith", "出生");


    private String name;
    private String des;

    EventType(String name, String des) {
        this.name = name;
        this.des = des;
    }
}
