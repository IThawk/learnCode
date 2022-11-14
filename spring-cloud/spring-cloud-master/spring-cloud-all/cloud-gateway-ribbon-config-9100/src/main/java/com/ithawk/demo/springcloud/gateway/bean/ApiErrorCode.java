package com.ithawk.demo.springcloud.gateway.bean;

public enum ApiErrorCode {

    FAILED("失败", 403),

    SUCCESS("成功", 200) ;
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ApiErrorCode(String message, Integer code) {
        this.code = code;
        this.message = message;
    }
}
