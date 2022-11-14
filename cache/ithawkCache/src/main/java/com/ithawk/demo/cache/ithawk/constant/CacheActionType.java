package com.ithawk.demo.cache.ithawk.constant;

/**
 * @author ithawk
 * @projectName cache
 * @description: TODO
 * @date 2021/8/140:05
 */
public enum CacheActionType {

    QUERY("QUERY","查询缓存"),
    UPDATE("UPDATE","更新缓存");

    String code;
    String mng;
    CacheActionType(String code,String mng) {
        this.code = code;
        this.mng = mng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMng() {
        return mng;
    }

    public void setMng(String mng) {
        this.mng = mng;
    }
}
