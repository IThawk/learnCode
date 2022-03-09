package com.ithawk.learn.simple;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/3/717:07
 */
public class ConvertToBase7 {

    static class Solution {
        public String convertToBase7(int num) {

            StringBuilder stringBuilder = new StringBuilder();
            boolean s = num < 0;
            if (num < 0) {
                num = num / -1;
            }
            while (Math.abs(num) >= 7) {
                stringBuilder.append(num % 7);
                num = num / 7;

            }

            stringBuilder.append(num);
            if (s){
                stringBuilder.append("-").reverse();
            }else{
                stringBuilder.reverse();
            }
            stringBuilder.reverse();
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.convertToBase7(-70));
    }
}
