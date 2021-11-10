package com.ithawk.learn.simple;

import java.util.Arrays;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/416:23
 */
public class MajorityElement {

    class Solution {
        public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length/2];
        }
    }
}
