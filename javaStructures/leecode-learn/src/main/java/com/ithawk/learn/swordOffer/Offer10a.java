package com.ithawk.learn.swordOffer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n级的台阶总共有多少种跳法。

 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Offer10a {
    public int fib(int n) {
        List<Integer> dp = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            if (i == 0) {
                dp.add(1);
            } else if (i == 1) {
                dp.add(1);
            } else {
                dp.add((dp.get(i - 1) + dp.get(i - 2)) % 1000000007);
            }

        }
        return dp.get(n);
    }

    public static void main(String[] args) {
        Offer10a offer10 = new Offer10a();
        System.out.println(offer10.fib(7));
        ;
    }
}