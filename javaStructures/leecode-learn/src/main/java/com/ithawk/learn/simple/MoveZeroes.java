package com.ithawk.learn.simple;

public class MoveZeroes {
    public static void main(String[] args) {
        int[] in = {1, 2, 0, 3, 0, 4,0,6};

        new Solution().moveZeroes(in);
        System.out.println();
    }

    /**
     * <p>
     *     给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     *
     * 示例:
     *
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 说明:
     *
     * 必须在原数组上操作，不能拷贝额外的数组。
     * 尽量减少操作次数。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/move-zeroes
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    static class Solution {
        public void moveZeroes(int[] nums) {

            int length;
            if (nums == null || (length = nums.length) == 0) {
                return;
            }
            int j = 0;
            for (int i = 0; i < length; i++) {
                if (nums[i] != 0) {
                    if (i > j) {// #1
                        nums[j] = nums[i];
                        nums[i] = 0;
                    }
                    j++;
                }
            }
        }
    }
}
