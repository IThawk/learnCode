package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     https://leetcode-cn.com/problems/group-anagrams/
 *     给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 *
 * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母都恰好只用一次。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 *
 * 输入: strs = [""]
 * 输出: [[""]]
 * 示例 3:
 *
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/group-anagrams
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description:
 * @date 2021/10/2819:32
 */
public class GroupAnagrams {
    static class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> re = new ArrayList<>();
            HashMap<String, List<String>> h = new HashMap<>();
            for (String ns : strs) {
                char[] chars = new char[ns.length()];
                for (int i = 0; i < ns.length(); i++) {
                    chars[i] = ns.charAt(i);
                    for (int j = 0; j < i; j++) {
                        if (ns.charAt(i) >= chars[j]) {
//                            chars[j] = ns.charAt(j);
                        } else {
                            //全部的数据后移
                            for (int k = i; k > j; k--) {
                                chars[k] = chars[k - 1];
                            }
                            chars[j] = ns.charAt(i);
                            break;
                        }
                    }
                }

                String key = new String(chars);
                if (h.containsKey(key)) {
                    h.get(key).add(ns);
                } else {
                    List<String> s = new ArrayList<>();
                    s.add(ns);
                    h.put(key, s);
                }
            }
            re.addAll(h.values());
            return re;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> s = solution.groupAnagrams(strs);
        System.out.println();
    }
}
