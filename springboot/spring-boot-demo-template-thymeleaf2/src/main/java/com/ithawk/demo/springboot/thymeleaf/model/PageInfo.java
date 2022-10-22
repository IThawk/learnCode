package com.ithawk.demo.springboot.thymeleaf.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
    public class PageInfo{
        private String pageName;
        private String pageCode;
        private String  riskCode;

    }