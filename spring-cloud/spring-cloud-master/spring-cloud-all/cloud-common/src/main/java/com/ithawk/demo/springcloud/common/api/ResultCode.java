package com.ithawk.demo.springcloud.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode {
    FAILED(500, "操作失败"),
    FORBIDDEN(403, "没有相关权限"),
    SUCCESS(200, "操作成功"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    VALIDATE_FAILED(404, "参数检验失败");


    private long code;
    private String message;


    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
