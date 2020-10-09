package com.ithawk.learn.leetCode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-17 13:14
 */
public class Intersection {
    public static void main(String[] args) {
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
        int[] s = new Solution().intersect(nums1, nums2);
        System.out.println(s);
    }

    /**
     * <p>
     * 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2,2]
     * 示例 2:
     * <p>
     * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出: [4,9]
     * 说明：
     * <p>
     * 输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     * 我们可以不考虑输出结果的顺序。
     * 进阶:
     * <p>
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     * 如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public int[] intersect(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            List<Integer> list = new ArrayList<>();
            int k = 0;
            if (nums1.length >= nums2.length) {
                for (int i = 0; i < nums1.length; i++) {
                    for (int j = k; j < nums2.length; j++) {
                        if (nums1[i] == nums2[j]) {
                            list.add(nums2[j]);
                            k = j + 1;
                            break;
                        }
                    }
                }
            } else {
                return intersect(nums2, nums1);
            }
            int[] re = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                re[i] = list.get(i);
            }

            return re;
        }
    }
}
