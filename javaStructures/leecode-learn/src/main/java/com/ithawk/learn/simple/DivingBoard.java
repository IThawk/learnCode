package com.ithawk.learn.simple;


import java.util.Set;

public class DivingBoard {

    class Solution {
        public int[] divingBoard(int shorter, int longer, int k) {
            if (k == 0) {
                return new int[0];
            }
            if (shorter == longer) {
                int[] r = {k * shorter};
                return r;
            }
            int[] r = new int[k+1];
            for (int i = 0; i <= k; i++) {
                r[i] = longer * i + shorter * (k - i);
            }
            return r;
        }
    }
}
