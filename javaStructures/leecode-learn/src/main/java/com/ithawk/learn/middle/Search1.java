package com.ithawk.learn.middle;

import com.ithawk.learn.difficult.LongestValidParentheses;

/**
 * <p>
 * <p>
 * <p>
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * <p>
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * <p>
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </P>
 *
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/2417:17
 */
public class Search1 {

    static class Solution {


        public String countAndSay(int n) {
            String[] strs = new String[n];
            strs[0] = "1";
            for (int i = 1; i < n; i++) {
                strs[i] = countAndSay(strs[i - 1]);
            }
            return strs[n-1];
        }

        public String countAndSay(String n) {
            StringBuilder stringBuilder = new StringBuilder();
            int num = 0;
            int q = Integer.parseInt(n.charAt(0) + "");

            for (int i = 0; i < n.length(); i++) {
                int k = Integer.parseInt(n.charAt(i) + "");
                if (q == k) {
                    num++;
                } else {
                    stringBuilder.append(num).append(q);
                    q = k;
                    num = 1;
                }
            }
            stringBuilder.append(num).append(q);
            return stringBuilder.toString();
        }

        public int longestValidParentheses(String s) {
            int maxans = 0;
            int[] dp = new int[s.length()];
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == ')') {
                    if (s.charAt(i - 1) == '(') {
                        dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                    } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                        dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                    }
                    maxans = Math.max(maxans, dp[i]);
                }
            }
            return maxans;
        }

        public int search(int[] nums, int target) {
            int font = 0;
            int end = nums.length - 1;

            while (font <= end) {
                int min = (font + end) / 2;
                if (target == nums[min]) {
                    return min;
                }
                if (target == nums[font]) {
                    return font;
                }
                if (target == nums[end]) {
                    return end;
                }
                //前面为升序

                if (nums[min] > nums[font] && nums[end] < nums[font]) {
                    if (target > nums[font] && target > nums[min]) {
                        font = min + 1;
                    } else if (target > nums[font] && target < nums[min]) {
                        end = min - 1;
                    } else {
                        font = min + 1;
                    }
                } else if (nums[min] < nums[font] && nums[end] < nums[font]) {//后面为升序
                    if (target > nums[font]) {
                        end = min - 1;
                    } else if (target > nums[min] && target < nums[end]) {
                        font = min + 1;
                    } else {
                        end = min - 1;
                    }
                } else { //youx
                    if (target > nums[min]) {
                        font = min + 1;
                    } else {
                        end = min - 1;
                    }
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3, 4, 5, 1, 2};
        System.out.println(solution.search(nums, 2));
        System.out.println(solution.countAndSay(5));
    }
}
