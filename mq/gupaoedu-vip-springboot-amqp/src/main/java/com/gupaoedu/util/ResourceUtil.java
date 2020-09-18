package com.gupaoedu.util;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("gupaomq");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
