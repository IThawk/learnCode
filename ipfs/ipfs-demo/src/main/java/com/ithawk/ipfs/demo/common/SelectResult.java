package com.ithawk.ipfs.demo.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectResult {
    private Integer status;
    private String result;
}