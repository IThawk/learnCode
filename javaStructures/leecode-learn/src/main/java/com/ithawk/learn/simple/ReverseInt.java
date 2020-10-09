package com.ithawk.learn.simple;

import java.util.ArrayList;
import java.util.List;

public class ReverseInt {

    public static void main(String[] args) {
        System.out.println(new Solution().reverse(-2147483412));
    }

    /**
     * <P>
     *     给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     *
     * 示例 1:
     *
     * 输入: 123
     * 输出: 321
     *  示例 2:
     *
     * 输入: -123
     * 输出: -321
     * 示例 3:
     *
     * 输入: 120
     * 输出: 21
     * 注意:
     *
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-integer
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </P>
     */
    static class Solution {
        public int reverse(int x) {
            int ans = 0;
            while (x != 0) {
                int pop = x % 10;
                if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
                    return 0;
                if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
                    return 0;
                ans = ans * 10 + pop;
                x /= 10;
            }
            return ans;

        }
    }
}
