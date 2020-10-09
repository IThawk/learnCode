package com.ithawk.learn.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * <p>
 *  
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 *
 * @author IThawk
 * @version V1.0
 * @description: 两数之和
 * @date 2020-04-18 16:53
 */
public class AddTwoNum {
    public static void main(String[] args) {
        int[] src = {2, 7, 11, 15};
        int[] test = new Solution().twoSum(src, 9);
        System.out.println("TEST RESULT IS : " + test[0] + " " + test[1]);
    }

    static class Solution {

        /**
         * @return int[]
         * @description: 暴力解题法
         * @author IThawk
         * @date 2020/4/18 17:18
         * @param: nums
         * @param: target
         */
        public int[] twoSum(int[] nums, int target) {

            int[] result = new int[2];
            for (int i = 0; i < nums.length - 1; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    int sum = nums[i] + nums[j];
                    if (target == sum) {
                        result[0] = i;
                        result[1] = j;
                        break;
                    }
                }
            }
            return result;
        }

        /**
         * @return int[]
         * @description: 先加入一个map中，再取值
         * @author IThawk
         * @date 2020/4/18 17:21
         * @param: nums
         * @param: target
         */
        public int[] twoSum2(int[] nums, int target) {
            int[] result = new int[2];
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i], i);
            }
            for (int i = 0; i < nums.length; i++) {
                int complements = target - nums[i];
                if (map.containsKey(complements) && map.get(complements) != i) {
                    result[0] = i;
                    result[1] = map.get(complements);
                    break;
                }
            }
            return result;

        }

        /**
         * @return int[]
         * @description: 直接放入map中，过程中查找数据
         * @author IThawk
         * @date 2020/4/18 17:22
         * @param: nums
         * @param: target
         */
        public int[] twoSum3(int[] nums, int target) {
            int[] result = new int[2];
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (map.containsKey(complement)) {
                    result[0] = i;
                    result[1] = map.get(complement);
                    break;
                }
                map.put(nums[i], i);
            }
            return result;
        }


    }
}

