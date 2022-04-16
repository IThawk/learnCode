package com.ithawk.demo.spring5.webflux.util;


import com.ithawk.demo.spring5.webflux.exception.StudentException;

import java.util.stream.Stream;

// 自定义校验工具
public class ValidateUtil {
    // 指定无效姓名列表
    private static final String[] INVALIDE_NAME = {"admin", "administrator"};

    // 对姓名进行校验
    public static void valideName(String name) {
        Stream.of(INVALIDE_NAME)
                // 比对的值为true，则通过过滤，该值将继续保留在流中
                .filter(invalideName -> name.equalsIgnoreCase(invalideName))
                .findAny()    // 返回Optional
                .ifPresent(inName -> {
                    throw new StudentException("name", name, "使用了非法姓名");}
                 );
    }
}
