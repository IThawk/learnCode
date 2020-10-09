package com.ithawk.learn.difficult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PalindromePairs {
    public static void main(String[] args) {
        System.out.println(new Solution().checkPalindromePairs("la","dal") );
    }

    /**
     * <p>
     *     给定一组 互不相同 的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：["abcd","dcba","lls","s","sssll"]
     * 输出：[[0,1],[1,0],[3,2],[2,4]]
     * 解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
     * 示例 2：
     *
     * 输入：["bat","tab","cat"]
     * 输出：[[0,1],[1,0]]
     * 解释：可拼接成的回文串为 ["battab","tabbat"]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/palindrome-pairs
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    static class Solution {
        public List<List<Integer>> palindromePairs(String[] words) {

            List<List<Integer>> lists = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                for (int j = i+1; j < words.length; j++) {
             if (words[i].equals(words[j])){
                 continue;
             }
                    if (checkPalindromePairs(words[i],words[j])){
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        list.add(j);
                        lists.add(list);
                    }
                    if (checkPalindromePairs(words[j],words[i])){
                        List<Integer> list = new ArrayList<>();
                        list.add(j);
                        list.add(i);
                        lists.add(list);
                    }

                }
            }
            return lists;
        }

        public boolean checkPalindromePairs(String first, String second) {
            String re = first + second;
            String re1 =new StringBuffer(re).reverse().toString();
            if (re.equals(re1)){
                return true;
            }else{
                return false;
            }
//            System.out.println(re1);
//            int i = 0, j = re.length() - 1;
//            while (i < j) {
//
//                if (re.charAt(i)!=re.charAt(j)){
//                    return false;
//                }
//                i++;
//                j--;
//            }
//
//            return true;
        }
    }



    class Solution1 {

        /**
         * 字典树
         */
        public class Trie {
            Trie[] next;
            //end表示这目前这棵树是那一个索引单词的结尾
            int end;

            public Trie() {
                this.next =  new Trie[26];
                this.end = -1;
            }
            //由于待会儿用到字典树的时候，是找目标串的翻转字符串，所以插入的时候，应该是倒序插入
            public void insert(String s, int endNum){
                char[] chars = s.toCharArray();
                Trie cur = this;
                for (int i = 0; i < chars.length; i++) {
                    int index = chars[i] - 'a';
                    if (cur.next[index] == null) cur.next[index] = new Trie();
                    cur = cur.next[index];
                }
                cur.end = endNum;
            }
        }
        public List<List<Integer>> palindromePairs(String[] words) {
            //构建字典树
            Trie trie = new Trie();
            for (int i = 0; i < words.length; i++) {
                trie.insert(reverse(words[i]), i);
            }
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                //先去字典树中找整个单词的翻转作为特殊情况考虑
                int index = findWord(trie, word);
                if (index != -1 && index != i) {
                    addRes(res, i, index);
                }
                //还有一种特殊情况，我也是提交了没有通过才发现的，就是单词里有空字符串"",
                //这意味他可以放在任何回文串的首尾
                if (isPalindrome(word)) {
                    index = findWord(trie, "");
                    if (index != -1 && index != i) addRes(res, i, index);
                }
                for (int j = 0; j < word.length(); j++) {
                    String ls = word.substring(0, j + 1);
                    String rs = word.substring(j + 1);
                    //先判断前半部分[0, j]是不是回文串
                    if (isPalindrome(ls)) {
                        int pre = findWord(trie, rs);
                        if (pre != -1 && pre != i) addRes(res, pre, i);
                    }
                    //再判断[j + 1, end],一定要加j != word.length() - 1,要不然会和上面找整个字符串的翻转重复
                    if (j != word.length() - 1 && isPalindrome(rs)) {
                        int after = findWord(trie, ls);
                        if (after != -1 && after != i) addRes(res, i, after);
                    }
                }
            }
            return res;
        }

        private void addRes(List<List<Integer>> res, int i, int j) {
            List<Integer> list = new ArrayList<>();
            list.add(i);
            list.add(j);
            res.add(list);
        }

        private boolean isPalindrome(String s) {
            int i = 0;
            int j = s.length() - 1;
            char[] letters = s.toCharArray();
            while (i < j) {
                if (letters[i] != letters[j]) return false;
                i++;
                j--;
            }
            return true;
        }

        private int findWord(Trie trie, String word) {
            Trie cur = trie;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int temp = chars[i] - 'a';
                if (cur.next[temp] == null) return -1;
                else cur = cur.next[temp];
            }
            return cur.end;
        }

        private String reverse(String word) {
            StringBuilder sb = new StringBuilder(word);
            return sb.reverse().toString();
        }

    }

}
