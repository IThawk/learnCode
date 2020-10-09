package com.ithawk.learn.leetCode.array;


/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class MoveZero {
    public static void main(String[] args) {
        int[] nums = {0,0,1};
//        Solution s = new Solution();
        new Solution().moveZeroes(nums);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 示例:
     * <p>
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 说明:
     * <p>
     * 必须在原数组上操作，不能拷贝额外的数组。
     * 尽量减少操作次数。
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public void moveZeroes(int[] nums) {
            int k = 0;
            for (int i = 0; i < nums.length; ) {
                if (i >= nums.length - k) {
                    return;
                }
                if (nums[i] == 0) {
                    k++;
                    for (int j = i; j < nums.length - k; j++) {
                        int tem = nums[j];
                        nums[j] = nums[j + 1];
                        nums[j + 1] = tem;
                    }
                } else {
                    i++;
                }
            }

        }
    }
}
