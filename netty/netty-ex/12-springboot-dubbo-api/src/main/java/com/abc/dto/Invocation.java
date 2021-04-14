package com.abc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC远程调用信息
 */
@Data
public class Invocation implements Serializable {
    /**
     * 接口名
     */
    private String className;
    /**
     * 要远程调用的方法名
     */
    private String methodName;
    /**
     * 方法参数类型列表
     */
    private Class<?>[] paramTypes;
    /**
     * 方法参数值
     */
    private Object[] paramValues;
    /**
     * 要调用的业务接口实现类的功能性前辍
     */
    private String prefix;

}
