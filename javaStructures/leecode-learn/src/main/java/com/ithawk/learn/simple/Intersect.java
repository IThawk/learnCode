package com.ithawk.learn.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intersect {
    public static void main(String[] args) {
        int[] num1 = {2};
        int [] num2= {1,2,2,1};
        int[] re = new Solution().intersect(num1,num2);
        System.out.println();
    }

    /**
     * <p>
     *
     给定两个数组，编写一个函数来计算它们的交集。

     示例 1:

     输入: nums1 = [1,2,2,1], nums2 = [2,2]
     输出: [2,2]
     示例 2:

     输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     输出: [4,9]
     说明：

     输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     我们可以不考虑输出结果的顺序。
     * </p>
     */
    static class Solution {
        public int[] intersect(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            List<Integer> list = new ArrayList<Integer>();
            int j = 0;
            if (nums1.length>nums2.length){
                for (int i = 0; i < nums1.length; i++) {
                    if (j > nums2.length - 1)
                        break;
                    for (int q = j; q < nums2.length; q++) {
                        if (nums1[i]>=nums2[q]){
                            j = q+1;
                        }
                        if (nums1[i] == nums2[q]) {
                            list.add(nums1[i]);
                            break;
                        }


                    }
                }
            }else{
                for (int i = 0; i < nums2.length; i++) {
                    if (j > nums1.length - 1)
                        break;
                    for (int q = j; q < nums1.length; q++) {
                        if (nums2[i]>=nums1[q]){
                            j = q+1;
                        }
                        if (nums2[i] == nums1[q]) {
                            list.add(nums2[i]);
                            break;
                        }


                    }
                }
            }


            int[] re = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                re[i] = list.get(i);
            }
            return re;
        }
    }
}
