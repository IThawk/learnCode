package com.ithawk.demo.springboot.ex.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomeService {
    private String prefix;
    private String surfix;

    // 当前Starter的核心功能实现方法
    public String wrap(String word) {
        return prefix + word + surfix;
    }
}
