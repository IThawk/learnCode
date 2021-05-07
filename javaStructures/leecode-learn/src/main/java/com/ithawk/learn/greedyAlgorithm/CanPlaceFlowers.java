package com.ithawk.learn.greedyAlgorithm;

/**
 * https://leetcode-cn.com/problems/can-place-flowers/
 */
public class CanPlaceFlowers {
    public static void main(String[] args) {
        int[] i = {1, 0, 0, 0, 0, 1};

        canPlaceFlowers(i, 2);
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed.length == 0) {
            return false;
        }
        int sum = 0;
        if (flowerbed.length == 1) {
            if (flowerbed[0] == 0) {
                sum = 1;
            }

        }

        for (int i = 0; i < flowerbed.length; i++) {
            if (i < flowerbed.length - 1 && flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
                if (i > 0 && flowerbed[i - 1] == 0) {
                    sum++;
                    i += 1;
                } else if (i == 0) {
                    sum++;
                    i += 1;
                }

            } else if (i == flowerbed.length - 1 && flowerbed[i] == 0) {
                if (i > 0 && flowerbed[i - 1] == 0) {
                    sum++;
                    i += 1;
                }
            }
        }
        if (sum >= n) {
            return true;
        }
        return false;
    }

    public static boolean canPlaceFlowers1(int[] flowerbed, int n) {
        if (flowerbed.length == 0) {
            return false;
        }
        int sum = 0;
        int[] jk = new int[flowerbed.length + 2];
        for (int i = 1; i < flowerbed.length + 1; i++) {
            jk[i] = flowerbed[i - 1];
        }
        for (int i = 1; i < jk.length - 1; i++) {
            if (jk[i-1]==0&& jk[i]==0&& jk[i+1]==0){
                sum++;
                if (sum >= n) {
                    return true;
                }
                i++;
            }
        }
        if (sum >= n) {
            return true;
        }
        return false;
    }
}
