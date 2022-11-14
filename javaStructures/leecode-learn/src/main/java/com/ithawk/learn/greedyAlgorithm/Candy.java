package com.ithawk.learn.greedyAlgorithm;

public class Candy {

    public static void main(String[] args) {
        int[] i = {1,0,2};
        candy(i);
    }

    /**
     * <p>
     *     https://leetcode-cn.com/problems/candy/submissions/
     * </p>
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {

        if (ratings.length == 0) {
            return 0;
        }
        if (ratings.length == 1) {
            return 1;
        }
        int[] rateArray = new int[ratings.length];

        for(int k = 0; k < ratings.length; k++){
            rateArray[k] = 1;
        }

        int sum = 0;
        for (int k = 0; k < ratings.length-1; k++) {
            if (ratings[k] < ratings[k+1]) {
                rateArray[k+1] = Math.max(rateArray[k]+1,rateArray[k+1]);
            }
        }

        for (int k = ratings.length-1; k >0; k--) {
            if (ratings[k] < ratings[k-1]) {
                rateArray[k-1] =  Math.max(rateArray[k]+1,rateArray[k-1]);
            }
        }
        for(int k = 0; k < ratings.length; k++){
            sum +=rateArray[k] ;
        }

        return sum;
    }
}
