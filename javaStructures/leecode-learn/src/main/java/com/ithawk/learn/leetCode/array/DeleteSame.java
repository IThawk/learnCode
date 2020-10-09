package com.ithawk.learn.leetCode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 16:27
 */
public class DeleteSame {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 6, 6, 7};
//        Solution s = new Solution();
        System.out.println(new Solution().removeDuplicates(nums));
    }

    /**
     * <p>
     *     给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     *
     *
     *
     * 示例 1:
     *
     * 给定数组 nums = [1,1,2],
     *
     * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     * 示例 2:
     *
     * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
     *
     * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/21/
     * </p>
     * @description: 删除排序数组中的重复项
     * @author IThawk
     * @date 2020/5/16 17:10
     * @param: null
     * @return
     */
    public static class Solution {

        public int removeDuplicates(int[] nums) {

            int j = 0;
            int current = 0;
            for (int i : nums) {
                if (j != 0 && current == i)
                    continue;
                nums[j] = i;
                j++;
                current = i;
            }
            return j;
        }
    }
}
