package com.ithawk.demo.javaBase.arthas.bean;

import java.io.Serializable;

/**
 * @author ithawk
 * @projectName javaBase
 * @description: TODO
 * @date 2021/12/3016:37
 */
public class JavaProcess implements Serializable {

    String processId;

    String processName;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
