package com.ithawk.learn.difficult;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class LargestRectangleArea {
    public static void main(String[] args) {
        int[] heights = {9,0};
        Solution solution = new Solution();
        System.out.println(solution.largestRectangleArea(heights));
        ;
    }

    static class Solution {
        public int largestRectangleArea(int[] heights) {
            if (heights.length < 1) {
                return 0;
            }
            if (heights.length == 1) {
                return heights[0];
            }
            int max = 0;
            int minH = heights[heights.length / 2-1];
            for (int i = heights.length / 2-1, j = heights.length / 2-1; i >= 0 || j <= heights.length-1 ; ) {
                if (i < 0 || j > heights.length-1 ){
                    break;
                }
                minH = Math.min(minH, Math.min(heights[i], heights[j]));
                max = Math.max(max, minH * (j - i + 1));
                if (heights[i] > heights[j] && j < heights.length-1) {
                    j++;
                } else if (i > 0) {
                    i--;
                } else if (j < heights.length-1) {
                    j++;
                }else{
                    i--;
                }
            }
            return max;

        }
    }
}
