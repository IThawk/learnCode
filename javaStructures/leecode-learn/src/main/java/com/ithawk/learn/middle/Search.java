package com.ithawk.learn.middle;

public class Search {

    /**
     * @className https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
     * @description:
     * @author IThawk
     * @date 2021/8/20 16:12
     */
    class Solution {
        public int search(int[] nums, int target) {

            int left =0;
            int right = nums.length-1;
            while (left<right){
                int min = (left+right+1)/2;
                //左边的是递增 
                if (nums[min]>nums[left]){

                }
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
