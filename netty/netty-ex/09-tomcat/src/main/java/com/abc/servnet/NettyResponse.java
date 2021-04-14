package com.abc.servnet;

/**
 * ServNet规范之响应规范
 */
public interface NettyResponse {
    // 将响应写入到Channel
    void write(String content) throws Exception;
}
