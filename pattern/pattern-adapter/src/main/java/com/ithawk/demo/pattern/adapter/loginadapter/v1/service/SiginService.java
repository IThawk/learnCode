package com.ithawk.demo.pattern.adapter.loginadapter.v1.service;

import com.ithawk.demo.pattern.adapter.loginadapter.Member;
import com.ithawk.demo.pattern.adapter.loginadapter.ResultMsg;


public class SiginService {

    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg regist(String username, String password){
        return  new ResultMsg(200,"注册成功",new Member());
    }


    /**
     * 登录的方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg login(String username,String password){
        return null;
    }

}
