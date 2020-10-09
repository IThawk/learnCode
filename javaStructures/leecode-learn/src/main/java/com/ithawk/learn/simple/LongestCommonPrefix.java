package com.ithawk.learn.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-06-15 23:03
 */
public class LongestCommonPrefix {


    /**
     * <P>
     *     编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * 示例 1:
     *
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     * 示例 2:
     *
     * 输入: ["dog","racecar","car"]
     * 输出: ""
     * 解释: 输入不存在公共前缀。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-common-prefix
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </P>
     */
    static class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }
            int minLength = Integer.MAX_VALUE;
            for (String str : strs) {
                minLength = Math.min(minLength, str.length());
            }
            int low = 0, high = minLength;
            while (low < high) {
                int mid = (high - low + 1) / 2 + low;
                if (isCommonPrefix(strs, mid)) {
                    low = mid;
                } else {
                    high = mid - 1;
                }
            }
            return strs[0].substring(0, low);
        }

        public boolean isCommonPrefix(String[] strs, int length) {
            String str0 = strs[0].substring(0, length);
            int count = strs.length;
            for (int i = 1; i < count; i++) {
                String str = strs[i];
                for (int j = 0; j < length; j++) {
                    if (str0.charAt(j) != str.charAt(j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
