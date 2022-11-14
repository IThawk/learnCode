package com.ithawk.demo.pattern.adapter.loginadapter.v2.adapters;

import com.ithawk.demo.pattern.adapter.loginadapter.ResultMsg;

/**
 * 在适配器里面，这个接口是可有可无，不要跟模板模式混淆
 * 模板模式一定是抽象类，而这里仅仅只是一个接口
 */
public interface LoginAdapter {

    boolean support(Object adapter);

    ResultMsg login(String id, Object adapter);

}
