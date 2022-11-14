package com.ithawk.demo.serializer;

import java.util.HashMap;
import java.util.Map;

public class TestStack {
    public static void main(String[] args) {
        Integer d = 1;

        Map<String,String> s = new HashMap<>();

        t(d);
        d= t(d);
        System.out.println(d);
//        test(0);
    }

    public static void test(int i) {
        if (i > 20000)
            return;
        i++;
        test(i);
        System.out.println(i);


    }

    public static int t(int f) {
        f = 121;
        return 111;
    }
}
