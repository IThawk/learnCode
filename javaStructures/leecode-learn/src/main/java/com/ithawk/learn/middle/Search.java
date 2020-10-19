package com.ithawk.learn.middle;

public class Search {

    class Solution {
        public int search(int[] nums, int target) {

            if (target>nums[0]&&target<nums[nums.length/2]){

            }

            for(int i =0 ;i<nums.length;i++){
                if (nums[i]==target){
                    return i;
                }
            }
            return -1;
        }
    }
}
