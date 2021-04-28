package com.ithawk.learn.springboot.utils;

import com.ithawk.learn.springboot.service.OrmUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-13 23:28
 */
@Getter
@AllArgsConstructor
public enum ClassEnum {
    CLASS_ENUM (OrmUserService.class);
    private Class<?> maker;
}
