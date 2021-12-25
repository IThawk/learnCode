package com.ithawk.demo.rabbitmq.springboot.v1.util;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("mq");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
