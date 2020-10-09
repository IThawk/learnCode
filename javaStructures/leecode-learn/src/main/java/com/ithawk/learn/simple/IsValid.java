package com.ithawk.learn.simple;

import java.util.Stack;

public class IsValid {
    class Solution {
        public boolean isValid(String s) {

            Stack<Character> a = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                switch (s.charAt(i)){
                    case '}':
                        if (a.empty()){
                            return false;
                        }
                        char b = a.pop();
                        if (b!='{'){
                            return false;
                        }
                        break;
                    case ']':
                        if (a.empty()){
                            return false;
                        }
                        char c = a.pop();
                        if (c!='['){
                            return false;
                        }
                        break;
                    case ')':
                        if (a.empty()){
                            return false;
                        }
                        char d = a.pop();
                        if (d!='('){
                            return false;
                        }
                        break;
                    default:
                        a.push(s.charAt(i));
                        break;
                }
            }
            if (a.empty()){
                return true;
            }
            return false;
        }
    }
}
