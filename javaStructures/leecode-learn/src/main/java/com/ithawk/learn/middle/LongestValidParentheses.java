package com.ithawk.learn.middle;

import java.util.*;

public class LongestValidParentheses {
    public static void main(String[] args) {
        System.out.println(new Solution().longestValidParentheses(")()())()()("));
    }

    static class Solution {
        public int longestValidParentheses(String s) {


            Set<Integer> list = new HashSet<>();
            Stack<Character> stack = new Stack<>();
            int temp = 0;
            for (int i = 0; i < s.length(); i++) {
                int r = 0;
                for (int j = i; j < s.length(); j++) {

                    if ('(' == s.charAt(j)) {
                        stack.push('(');
                    } else {
                        if (stack.isEmpty()){
                            stack.clear();
                            continue;
                        }
                        Character t = stack.pop();
                        if (t == '(') {
                            temp += 1;
                            if (stack.isEmpty()) {
                                r = r + temp * 2;
                                temp = 0;
                            }
                        }
                    }

                }
                temp = 0;
                list.add(r);
            }
            Object[] rs = list.toArray();
            Arrays.sort(rs);
            if (rs.length==0){
                return 0;
            }
            return (int) rs[rs.length - 1];
        }
    }
}
