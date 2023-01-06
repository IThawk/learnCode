//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ithawk.demo.code.mgr.bean;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    private long total;
    private List<T> items;
//    private boolean hasNext; // 是否有下一页。
    private int page ;
    private int perPage;
    private int pages;
}
