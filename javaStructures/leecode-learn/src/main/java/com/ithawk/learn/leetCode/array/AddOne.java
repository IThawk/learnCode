package com.ithawk.learn.leetCode.array;


import java.util.Arrays;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class AddOne {
    public static void main(String[] args) {
        int[] nums = {9, 9, 9, 9, 9, 9, 9};
//        Solution s = new Solution();
        int[] e = new Solution().plusOne(nums);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 示例 1:
     * <p>
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     * 示例 2:
     * <p>
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public int[] plusOne(int[] digits) {

            int j = 0;
            for (int i = digits.length - 1; i >= 0; i--) {
                digits[i] = digits[i] + 1;
                if (digits[i] != 10) {
                    break;
                } else {
                    digits[i] = 0;
                }
                j++;
            }
            if (j == digits.length) {
                int[] re = new int[digits.length + 1];
                re[0] = 1;
                return re;
            } else {
                return digits;
            }

        }
    }
}
