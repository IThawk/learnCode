package com.ithawk.learn.simple;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/3/717:46
 */
public class SearchInsert {

    class Solution {
        public int searchInsert(int[] nums, int target) {

            int head = 0;
            int end = nums.length - 1;
            int mid = (head + end) / 2;
            while (mid > head) {
                if (target <= nums[head]) {
                    return head;
                }
                if (target >= nums[end]) {
                    return end;
                }
                if (target == mid) {
                    return mid;
                }
                if (target > nums[mid]) {
                    head = mid;
                    mid = (head + mid) / 2;
                }
                if (target < nums[mid]) {
                    end = mid;
                    mid = (head + mid) / 2;
                }
            }
            return mid;
        }
    }
}
