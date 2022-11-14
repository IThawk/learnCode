package com.ithawk.learn.simple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author IThawk
 * @className MinWindow
 * @description: https://leetcode-cn.com/problems/minimum-window-substring/solution/zui-xiao-fu-gai-zi-chuan-by-leetcode-solution/
 * @date 2021/8/18 19:25
 */
class Sqrt {

    private static int mySqrt(int s) {
        if (s == 0) {
            return 0;
        }
        int l = 0;
        int r = s ;
        int min = (l + r) / 2;
        while (l + 1 < r) {
            if (min * min > s) {
                r = min;
                min = (l + min) / 2;
            } else if (min * min == s) {
                return min;
            } else {
                l = min;
                min = (r + min) / 2;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        //Input: S = "ADOBECODEBANC", T = "ABC"
        System.out.println(mySqrt(10));
        ;
    }
}