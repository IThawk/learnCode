package com.xxl.job.http.handler;

public class ScheduleResultResponse {

    public static final String SUCCESS_CODE = "1";
    public static final String ERROR_CODE = "0";

    private String code;
    
    private String message;

    public ScheduleResultResponse() {
        this.code = "1";
        this.message = "成功";
    }

    public ScheduleResultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
