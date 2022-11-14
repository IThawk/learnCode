package com.ithawk.demo.spring5.webflux.util;


import com.ithawk.demo.spring5.webflux.exception.StudentException;

import java.util.stream.Stream;

// 自定义校验工具
public class ValidateUtil {
    // 指定无效姓名列表
    private static final String[] INVALIDE_NAME = {"admin", "administrator"};

    public static void valideName(String name) {
        Stream.of(INVALIDE_NAME)
                .filter(invalideName -> invalideName.equalsIgnoreCase(name))
                .findAny()
                .ifPresent(invalidName -> {
                    throw new StudentException("name", invalidName, "是非法的");
                });
    }
}
