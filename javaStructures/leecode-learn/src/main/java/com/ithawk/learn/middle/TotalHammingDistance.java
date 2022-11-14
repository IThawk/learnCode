package com.ithawk.learn.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/2/1015:11
 */
public class TotalHammingDistance {
    static class Solution {
        public int totalHammingDistance(int[] nums) {

            int i = 0;
            Map<Integer, Integer> exit = new HashMap<>();
            for (int q = 0; q < nums.length - 1; q++) {
                for (int k = q + 1; k < nums.length; k++) {
                    int all = nums[q] ^ nums[k];

                    int j = 0;
                    if (exit.containsKey(all)) {
                        i += exit.get(all);
                        continue;
                    }
                    while (all > 0) {
                        if ((all & 1) == 1) {
                            j++;
                        }
                        all >>= 1;
                    }
                    i += j;
                    exit.put(nums[q] ^ nums[k], j);
                }
            }
            return i;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] i = {4, 14, 4};
        solution.totalHammingDistance(i);
    }

}
