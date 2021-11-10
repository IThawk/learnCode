package com.ithawk.learn.middle;

/**
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
            }
            return stringBuilder.toString();
        }

        public boolean compare(int w, int k) {
            int a = w;
            int b = k;
            while (a > 10 && b > 10) {
                int t = a;
                while (t > 10) {
                    t /= 10;
                }
                int q = b;
                while (q > 10) {
                    q /= 10;

                }
                if (t > q) {
                    return true;
                }
                a =  a - (a / 10) * 10;
                b = b - (b / 10) * 10;
            }


            int t = a;
            while (t >= 10) {
                t /= 10;
            }
            int l = b;
            while (l >= 10) {
                l /= 10;

            }
            if (t > l) {

                return true;
            }
            if (t==l){
                //比较第二位
                if(a>10){
                    a =Integer.parseInt(String.valueOf(a).charAt(1)+"");
                }
                if (b>10){
                    b =Integer.parseInt(String.valueOf(b).charAt(1)+"");
                }
                if (a>=b){
                    return true;
                }
            }
            return false;
        }

    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] d = {9, 5, 932, 96, 9};
        System.out.println(solution.largestNumber(d));

        int[] d1 = {3};
        System.out.println(solution.largestNumber(d1));


        int[] d2 = {10, 2};
        System.out.println(solution.largestNumber(d2));
    }
}
