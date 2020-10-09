package com.ithawk.learn.middle;

public class NextPermutation {

    public static void main(String[] args) {
        int[] n = {3,2,1};
        new Solution().nextPermutation(n);
        System.out.println("jjjj");
    }

    static class Solution {
        public void nextPermutation(int[] nums) {

            for (int i = nums.length - 1; i >= 0; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] > nums[j]) {
                        insert(nums,j,nums[i],i);
                        return;
                    }
                }
            }
            for (int i = 0; i <= nums.length / 2; i++) {
                int temp = nums[i];
                nums[i] = nums[nums.length - 1 - i];
                nums[nums.length - 1 - i] = temp;
            }
        }

        private void insert(int[] nums, int i, int k,int q) {
            for (int j = q; j > i; j--) {
                nums[j] = nums[j - 1];
            }
            nums[i] = k;

        }
    }
}
