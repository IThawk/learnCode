package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/1/1320:37
 */
public class Permute {

    static class Solution {
        public List<List<Integer>> permute(int[] nums) {

            List<List<Integer>> result = new ArrayList<>();

            List<Integer> result1 = new ArrayList<>();
            Set<Integer> con = new HashSet<>();
            doPermute(result, result1, con, nums, 0);
            return result;
        }

        private void doPermute(List<List<Integer>> result, List<Integer> result1, Set<Integer> con, int[] nums, int n) {
            if (nums.length == n) {
                result.add(new ArrayList<>(result1));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (!con.contains(i)) {
                    con.add(i);
                    result1.add(nums[i]);
                    doPermute(result, result1, con, nums, n+1);
                    //* *回溯
                    result1.remove(n);
                    con.remove(i);
                }

            }


        }

        private void doPermute1(List<Integer> result, int[] nums, Set<Integer> con, int n) {
            if (nums.length == n) {
                return;
            }
            n++;
            for (int i = 0; i < nums.length; i++) {
                if (con.contains(i)) {
                    continue;
                }
                con.add(i);
                result.add(nums[i]);
                break;

            }
            doPermute1(result, nums, con, n);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> lists = solution.permute(nums);
        System.out.println("........");
    }
}
