package com.kaikeba.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 制定一种处理器的标准
 */
public interface HttpServletHandler {
    void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
