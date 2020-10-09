package com.ithawk.learn.leetCode.array;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 17:12
 */
public class Rotate {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 6, 6, 7};
//        Solution s = new Solution();
        new Solution().rotate(nums, 1);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     * <p>
     * 示例 1:
     * <p>
     * 输入: [1,2,3,4,5,6,7] 和 k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     * 示例 2:
     * <p>
     * 输入: [-1,-100,3,99] 和 k = 2
     * 输出: [3,99,-1,-100]
     * 解释:
     * 向右旋转 1 步: [99,-1,-100,3]
     * 向右旋转 2 步: [3,99,-1,-100]
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 17:27
     * @param: null
     * @return
     */
    public static class Solution {

        public void rotate(int[] nums, int k) {

            for (int j = 0; j < k; j++) {
                for (int i = nums.length-1; i >0; i--){
                    int temp = nums[i];
                    nums[i] = nums[i-1];
                    nums[i-1] = temp;
                }
            }

        }
    }
}
