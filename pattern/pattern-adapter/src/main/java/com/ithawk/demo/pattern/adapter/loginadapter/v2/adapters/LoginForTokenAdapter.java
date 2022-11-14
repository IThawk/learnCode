package com.ithawk.demo.pattern.adapter.loginadapter.v2.adapters;

import com.ithawk.demo.pattern.adapter.loginadapter.ResultMsg;


public class LoginForTokenAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTokenAdapter;
    }

    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
