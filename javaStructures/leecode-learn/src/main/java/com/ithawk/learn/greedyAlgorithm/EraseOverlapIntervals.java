package com.ithawk.learn.greedyAlgorithm;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * https://leetcode-cn.com/problems/non-overlapping-intervals/
 */
public class EraseOverlapIntervals {
    public static void main(String[] args) {
        int[][] i = {{1,2},{1,1}, {2,4}};
        eraseOverlapIntervals(i);
    }
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> {

            return o1[1] - o2[1];
        });

        int n = intervals.length;
        int right = intervals[0][1];
        int ans = 1;
        for (int i = 1; i < n; ++i) {
            if (intervals[i][0] >= right) {
                ++ans;
                right = intervals[i][1];
            }
        }
        return n - ans;

    }
}
