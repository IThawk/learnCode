package com.gupaoedu.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: qingshan
 * @Date: 2019/7/17 16:41
 * @Description: 咕泡学院，只为更好的你
 */
public class TestTest {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<Integer>();

        for(int i=0; i<100; i++){
            set1.add(i);
            set1.remove(i-1);
        }
        System.out.println(set1.size());
    }

}
