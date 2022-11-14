package com.ithawk.demo.springcloud.gateway.bean;

/**
 * Ajax请求的返回内容:增删改
 * success:成功与否
 * message:失败原因
 */
public class AjaxResult<T> {

    private boolean success = true;
    private T message;
    private int code;

    public static AjaxResult<String> ok(String s) {
        return new AjaxResult<>(s, 200,true);
    }

    public static Object restResult(String s, ApiErrorCode success) {
        return new AjaxResult<>(s, 200,true);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private Object resultObj = null;

    public boolean isSuccess() {
        return success;
    }

    //链式编程,可以继续. 设置完成后自己对象返回
    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getMessage() {
        return message;
    }

    public AjaxResult setMessage(T message) {
        this.message = message;
        return this;
    }

    //默认成功
    public AjaxResult() {
        this.success = true;

        this.code = 200;
    }

    //失败调用
    public AjaxResult(T message, int code) {
        this.success = false;
        this.message = message;
        this.code = code;
    }

    public AjaxResult(T message, int code,boolean success) {
        this.success = success;
        this.message = message;
        this.code = code;
    }
    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resulObj) {
        this.resultObj = resultObj;
        return this;
    }

    //不要让我创建太多对象
    public static AjaxResult me() {
        return new AjaxResult();
    }

    public static void main(String[] args) {
        AjaxResult.me().setMessage("xxx").setSuccess(false);
    }
}
