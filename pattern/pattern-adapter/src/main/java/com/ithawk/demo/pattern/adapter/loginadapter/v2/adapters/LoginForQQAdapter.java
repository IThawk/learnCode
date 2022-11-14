package com.ithawk.demo.pattern.adapter.loginadapter.v2.adapters;

import com.ithawk.demo.pattern.adapter.loginadapter.ResultMsg;


public class LoginForQQAdapter implements LoginAdapter {

    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    public ResultMsg login(String id, Object adapter) {
        System.out.println(id + ":qq登入成功");
        return null;
    }
}
