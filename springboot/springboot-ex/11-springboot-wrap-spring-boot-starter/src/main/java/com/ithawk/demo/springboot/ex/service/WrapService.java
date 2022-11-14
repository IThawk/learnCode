package com.ithawk.demo.springboot.ex.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrapService {
    private String before;
    private String after;

    // starter的核心业务方法
    public String wrap(String word) {
        return before + word + after;
    }
}
