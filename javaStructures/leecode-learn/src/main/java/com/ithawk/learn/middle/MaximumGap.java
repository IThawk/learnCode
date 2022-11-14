package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <p>
 * https://leetcode-cn.com/problems/maximum-gap/
 * 164. 最大间距
 * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
 * <p>
 * 如果数组元素个数小于 2，则返回 0。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 * 示例 2:
 * <p>
 * 输入: [10]
 * 输出: 0
 * 解释: 数组元素个数小于 2，因此返回 0。
 * 说明:
 * <p>
 * 你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
 * 请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
 * </p>
 *
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/411:10
 */
public class MaximumGap {

    static class Solution {
        public int maximumGap(int[] nums) {
            if (nums.length < 2) {
                return 0;
            }
            if (nums.length == 2) {
                return Math.abs(nums[0] - nums[1]);
            }
            //1:排序
//            for (int i = 0; i < nums.length - 1; i++) {
//                for (int j = 1; j < nums.length-i; j++) {
//                    if (nums[j - 1] > nums[j]) {
//                        int temp = nums[j - 1];
//                        nums[j - 1] = nums[j];
//                        nums[j] = temp;
//                    }
//
//                }
//            }
            Arrays.sort(nums);
            int re = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                if (re < nums[i + 1] - nums[i]) {
                    re = nums[i + 1] - nums[i];
                }
            }
            return re;
        }


        public int maximumGap1(int[] nums) {
            if (nums.length < 2) {
                return 0;
            }
            if (nums.length == 2) {
                return Math.abs(nums[0] - nums[1]);
            }
            //1:排序
            int re = 0;
            //1:排序
            for (int i = 0; i < nums.length - 1; i++) {
                int k = 0;
                for (int j = 1; j < nums.length - i; j++) {
                    if (nums[j - 1] > nums[j]) {
                        int temp = nums[j - 1];
                        nums[j - 1] = nums[j];
                        nums[j] = temp;
                    }
                    k = j;
                }
                if (nums.length - i <= nums.length - 1) {
                    if (re < nums[nums.length - i] - nums[nums.length - i - 1]) {
                        re = nums[nums.length - i] - nums[nums.length - i - 1];
                    }
                }
            }
            if (re < nums[1] - nums[0]) {
                re = nums[1] - nums[0];
            }
            return re;

        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {494767408, 862126209, 213511142, 768240025, 631263193, 839199222, 990848796, 214568815, 540853864, 760724418, 980162605, 976743981, 168965760, 680875496, 256709197, 970953816, 948680062, 347254784, 732201399, 786249847, 782805044, 40906641, 674241381, 784330934, 175502610, 731105392, 424650824, 549764101, 986090032, 710542549, 249208107, 448419816, 527708664, 158499246, 223421723, 114552457, 466978424, 821491411, 19614107, 115389497, 902211438, 2644108, 744489871, 897895073, 372311214, 551142753, 933294894, 426217662, 217504874, 983488406, 516890023, 426853110, 971124103};
        solution.maximumGap1(nums);

    }


}

