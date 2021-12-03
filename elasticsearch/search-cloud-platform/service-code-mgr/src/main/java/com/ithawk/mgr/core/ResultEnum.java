package com.ithawk.mgr.core;

/**
* @Class: ResultEnum
* @Package
* @Description: 操作提示枚举类
* @Company:
*/
public enum ResultEnum {
    SUCCESS("200", "操作成功！"),
    PARAM_ISNULL("-400", "参数为空"),
    ERROR("-402", "操作失败！"),
    SERVER_ERROR("-500", "服务异常"),
    DATA_EXISTENT("-504", "数据不存在"),
    RESULT_EMPTY("-000", "查询内容为空"),

    not_system_api("404", "不是系统指定api"),
    repeat("666", "数据已存在"),
    http_error("-405", "请求异常");
    private String code;
    private String decs;

    public String getCode() {
        return code;
    }

    public String getDecs() {
        return decs;
    }

    ResultEnum(String code, String decs) {
        this.code = code;
        this.decs = decs;
    }

}
