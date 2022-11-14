package com.ithawk.learn.middle;

/**
 * <p>
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * <p>
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [10,2]
 * 输出："210"
 * 示例 2：
 * <p>
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 * 示例 3：
 * <p>
 * 输入：nums = [1]
 * 输出："1"
 * 示例 4：
 * <p>
 * 输入：nums = [10]
 * 输出："10"
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 *
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/416:51
 */
public class LargestNumber {

    static class Solution {
        public String largestNumber(int[] nums) {
            for (int i = 0; i < nums.length - 1; i++) {
                for (int j = 1; j < nums.length - i; j++) {
                    if (compare(nums[j - 1], nums[j])) {
                        int temp = nums[j - 1];
                        nums[j - 1] = nums[j];
                        nums[j] = temp;
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = nums.length - 1; i >= 0; i--) {
                stringBuilder.append(nums[i]);
                if (stringBuilder.charAt(0) == '0') {
                    stringBuilder.deleteCharAt(0);
                }
            }
            if (stringBuilder.length() == 0) {
                return "0";
            }
            return stringBuilder.toString();
        }

        /**
         * @className Solution
         * @description:  对比两个数 组合谁放在前面
         * @author IThawk
         * @date 2021/12/17 8:42
         */
        public boolean compare(int w, int k) {
            long iw = w * 10L;
            int tmp_k = k;
            while (tmp_k >= 10) {
                iw *= 10;
                tmp_k = tmp_k / 10;
            }
            long ik = k * 10L;
            int tmp_w = w;
            while (tmp_w >= 10) {
                ik *= 10;
                tmp_w = tmp_w / 10;
            }
            return (iw + k) >= (ik + w);
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] d = {9, 5, 932, 96, 9};
//        System.out.println(solution.largestNumber(d));
//
//        int[] d1 = {3};
//        System.out.println(solution.largestNumber(d1));
        System.out.println("1".startsWith("1"));

        int[] d2 = {10, 2};
        System.out.println(solution.largestNumber(d2));
    }
}
