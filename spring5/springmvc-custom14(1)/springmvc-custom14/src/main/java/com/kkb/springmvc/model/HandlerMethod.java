package com.kkb.springmvc.model;

import java.lang.reflect.Method;

/**
 * 注解方式下的处理器（Controller实例+对应的Method对象）
 */

public class HandlerMethod {
    private Object controller;

    private Method method;

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HandlerMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }
}
