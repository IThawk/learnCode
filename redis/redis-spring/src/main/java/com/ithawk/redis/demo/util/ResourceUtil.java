package com.ithawk.redis.demo.util;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("redis");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
