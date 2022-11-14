package com.ithawk.learn.middle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO  错误
 * @date 2021/10/2820:24
 */
public class Merge {
    static class Solution {
        public int[][] merge(int[][] intervals) {
            Set<Integer> s = new HashSet<>();
            for (int i = 0; i < intervals.length; i++) {
                if (s.contains(i)) {
                    continue;
                }
                for (int j = 0; j < intervals.length; j++) {
                    if (j == i || s.contains(j)) {
                        continue;
                    }
                    int[] k = intervals[i];
                    int[] q = intervals[j];
                    //被比较的区间大
                    if (k[1] <= q[1] && k[1] >= q[0]) {
                        if(k[0]>=q[0]){
                            k[0]=q[0];
                        }
                        if(k[1]<=q[1]){
                            k[1]=q[1];
                        }
                        //已经被合并
                        s.add(j);
                        continue;
                    }
                    //区间小
                    if (q[0] <= k[0] && q[1] >= k[0]) {
                        if(k[0]>=q[0]){
                            k[0]=q[0];
                        }
                        if(k[1]<=q[1]){
                            k[1]=q[1];
                        }
                        //已经被合并
                        s.add(j);
                        continue;
                    }
                    //////////////////////////////
                    //被比较的区间大
                    if (q[1] <= k[1] && q[1] >= k[0]) {
                        if(k[0]>=q[0]){
                            k[0]=q[0];
                        }
                        if(k[1]<=q[1]){
                            k[1]=q[1];
                        }
                        //已经被合并
                        s.add(j);
                        continue;
                    }
                    //区间小
                    if (k[0] <= q[0] && k[1] >= q[0]) {
                        if(k[0]>=q[0]){
                            k[0]=q[0];
                        }
                        if(k[1]<=q[1]){
                            k[1]=q[1];
                        }
                        //已经被合并
                        s.add(j);
                        continue;
                    }
                }
            }
            int[][] re = new int[intervals.length - s.size()][2];
            int j = 0;
            for (int i = 0; i < intervals.length; i++) {
                if (s.contains(i)) {
                    continue;
                }
                re[j] = intervals[i];
                j++;
            }
            return re;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] intervals = {{3,5},{0,0},{4,4},{0,2},{5,6},{4,5},{3,5},{1,3},{4,6},{4,6},{3,4}};
        int[][] merge = solution.merge(intervals);
        System.out.println();
    }
}
