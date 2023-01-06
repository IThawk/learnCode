//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ithawk.demo.code.mgr.bean;

import lombok.Data;

@Data
public class Result<T> {
    private int status;
    private String msg;
    private T data;
}
