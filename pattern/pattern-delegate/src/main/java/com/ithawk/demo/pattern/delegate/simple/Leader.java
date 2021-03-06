package com.ithawk.demo.pattern.delegate.simple;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Leader implements IEmployee {

    //初始化委派的人员
    private Map<String,IEmployee> targets = new HashMap<String,IEmployee>();

    public Leader() {
        targets.put("加密",new EmployeeA());
        targets.put("登录",new EmployeeB());
    }

    //项目经理自己不干活
    public void doing(String command){
        targets.get(command).doing(command);
    }

}
