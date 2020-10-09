package com.ithawk.demo.spring.v2.formework.aop.config;

import lombok.Data;

/**
 * Created by Tom on 2019/4/15.
 */
@Data
public class GPAopConfig {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;

}
