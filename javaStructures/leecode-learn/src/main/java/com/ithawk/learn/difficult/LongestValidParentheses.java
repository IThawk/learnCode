package com.ithawk.learn.difficult;

/**
 * <p>
 * 给你一个只包含 '('和 ')'的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * 示例 2：
 * <p>
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * 示例 3：
 * <p>
 * 输入：s = ""
 * 输出：0
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </P>
 *
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/1820:14
 */
public class LongestValidParentheses {

    static class Solution {
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
            int font =0;
            int end = nums.length-1;

            while (font<=end){
                int min = (font+end) / 2;
                if (target==nums[min]){
                    return min;
                }
                if (target==nums[font]){
                    return font;
                }
                if (target==nums[end]){
                    return end;
                }
                //前面为升序

                if (nums[min]>nums[font]&&nums[end]<nums[font]){
                    if(target>nums[font]&& target>nums[min]){
                        font = min+1;
                    }else if (target>nums[font]&& target<nums[min]){
                        end = min-1;
                    }else{
                        font=min+1;
                    }
                }else if (nums[min]<nums[font]&&nums[end]<nums[font]){//后面为升序
                    if(target>nums[font]){
                        end=min-1;
                    }else if (target>nums[min]&& target<nums[end]){
                        font = min+1;
                    }else{
                        end=min-1;
                    }
                }else{ //youx
                    if(target>nums[min]){
                        font=min+1;
                    }else{
                        end=min-1;
                    }
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3,4,5,1,2};
        System.out.println(solution.search(nums,2));;
    }
}
