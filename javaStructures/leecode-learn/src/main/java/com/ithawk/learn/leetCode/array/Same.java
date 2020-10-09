package com.ithawk.learn.leetCode.array;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class Same {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 6, 6, 7};
//        Solution s = new Solution();
        new Solution().containsDuplicate(nums);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     *     给定一个整数数组，判断是否存在重复元素。
     *
     * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     *
     *
     *
     * 示例 1:
     *
     * 输入: [1,2,3,1]
     * 输出: true
     * 示例 2:
     *
     * 输入: [1,2,3,4]
     * 输出: false
     * 示例 3:
     *
     * 输入: [1,1,1,3,3,4,3,2,4,2]
     * 输出: true
     * </p>
     * @description: //TODO
     * @author IThawk
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public boolean containsDuplicate(int[] nums) {
            Set<Integer> set = new HashSet<>(nums.length);
            for (int x: nums) {
                if (set.contains(x)) return true;
                set.add(x);
            }
            return false;
        }
    }
}
