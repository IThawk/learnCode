package com.ithawk.learn.leetCode.array;


import java.lang.reflect.Array;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class Sum {
    public static void main(String[] args) {
        int[] nums = {9, 9, 9, 9, 9, 9, 9};
//        Solution s = new Solution();
        int[] e = new Solution().twoSum(nums,18);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     *
     *
     * 示例:
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public int[] twoSum(int[] nums, int target) {
            int[] re = new int[2];
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (target == nums[i] + nums[j]) {
                        re[0] = i;
                        re[1] = j;
                        return re;
                    }
                }
            }
            return re;
        }
    }
}
