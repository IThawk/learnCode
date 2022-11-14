package com.ithawk.learn.middle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/2320:45
 */
public class FindDuplicate {

    class Solution {
        public int findDuplicate(int[] nums) {

//            Set<Integer> set = new HashSet<>();
//            for (int i = 0; i < nums.length; i++) {
//                if (set.contains(nums[i])) {
//                    return nums[i];
//                } else {
//                    set.add(nums[i]);
//                }
//            }
//            return 0;

            Arrays.sort(nums);
            for (int i=0;i<nums.length-1;i++){
                if (nums[i]==nums[i+1]){
                    return nums[i];
                }

            }
            return 0;
        }
    }
}
