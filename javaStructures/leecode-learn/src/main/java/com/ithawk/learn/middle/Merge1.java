package com.ithawk.learn.middle;

/**
 * <p>
 * https://leetcode-cn.com/problems/merge-sorted-array/
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 * 示例 2：
 * <p>
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * 示例 3：
 * <p>
 * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
 * 输出：[1]
 * 解释：需要合并的数组是 [] 和 [1] 。
 * 合并结果是 [1] 。
 * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 *
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/10/2910:50
 */
public class Merge1 {

    static class Solution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            //合并数据
            for (int i = 0; i < n; i++) {
                nums1[i + m] = nums2[i];
            }
            //数据排序
            for (int i = 0; i < nums1.length - 1; i++) {
                for (int j = i + 1; j < nums1.length; j++) {
                    if (nums1[i] > nums1[j]) {
                        int temp = nums1[i];
                        nums1[i] = nums1[j];
                        nums1[j] = temp;
                    }
                }
            }
        }


        /**
         * @className Solution
         * @description: 插入排序
         * @author IThawk
         * @date 2021/10/29 11:15
         */
        public void merge1(int[] nums1, int m, int[] nums2, int n) {
            //合并数据
            int j = 0;
            for (int i = 0; i < n; i++) {
                nums1[i+m] = nums2[i];
                //数据排序
                for (int k = j; k < m+i; k++) {
                    if (nums1[k] > nums2[i]) {
                        //数据全部向后移
                        for (int q = m + n - 1; q > k; q--) {
                            nums1[q] = nums1[q - 1];
                        }
                        nums1[k] = nums2[i];
                        j = k;
                        break;
                    }

                }

            }

        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = {4,5,6,0,0,0};
        int m = 3;
        int[] nums2 = {1,2,3};
        int n = 3;
        solution.merge1(nums1, m, nums2, n);
        System.out.println();
    }
}
