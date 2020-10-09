package com.ithawk.learn.leetCode.array;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class One {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 6, 6, 7};
//        Solution s = new Solution();
        new Solution().singleNumber(nums);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 说明：
     *
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     *
     * 输入: [2,2,1]
     * 输出: 1
     * 示例 2:
     *
     * 输入: [4,1,2,1,2]
     * 输出: 4
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public int singleNumber(int[] nums) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length-1; i += 2) {
                if (nums[i]!=nums[i+1])
                    return nums[i];
            }
            return nums[nums.length-1];
        }
    }
}
