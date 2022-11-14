package com.ithawk.demo.pattern.adapter.loginadapter.v2.adapters;

import com.ithawk.demo.pattern.adapter.loginadapter.ResultMsg;


public class LoginForWechatAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForWechatAdapter;
    }

    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
