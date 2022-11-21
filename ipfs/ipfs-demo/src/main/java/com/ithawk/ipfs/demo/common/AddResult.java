package com.ithawk.ipfs.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddResult {
    private Integer status;
    private String hash;
}