package com.ithawk.learn.middle;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/8/1911:45
 */
public class SearchRange {

    private static int[] searchRange(int[] j, int s) {
        int[] re = {-1, -1};
        if (j.length == 0) {
            return re;
        }
        int l = 0;
        int r = j.length;
        int min =0;
        if (j[min] == s) {
            int lmin = min;
            while (lmin != 0 && j[lmin - 1] == s) {
                lmin--;
            }
            int rmin = min;
            while (rmin != j.length - 1 && j[rmin + 1] == s) {
                rmin++;
            }
            re[0] = lmin;
            re[1] = rmin;
            return re;
        }
        min = j.length-1;
        if (j[min] == s) {
            int lmin = min;
            while (lmin != 0 && j[lmin - 1] == s) {
                lmin--;
            }
            int rmin = min;
            while (rmin != j.length - 1 && j[rmin + 1] == s) {
                rmin++;
            }
            re[0] = lmin;
            re[1] = rmin;
            return re;
        }
        while (l  +1< r) {
             min = (l + r) / 2;

            if (j[min] == s) {
                int lmin = min;
                while (lmin != 0 && j[lmin - 1] == s) {
                    lmin--;
                }
                int rmin = min;
                while (rmin != j.length - 1 && j[rmin + 1] == s) {
                    rmin++;
                }
                re[0] = lmin;
                re[1] = rmin;
                return re;
            } else if (j[min] > s) {
                r = min;
            } else {
                l = min;
            }
            if (min==0||min+1==j.length){
                break;
            }
        }
        return re;
    }

    public static void main(String[] args) {
        int[] in ={0,0,1,1,1,4,5,5};
        searchRange(in,4);
    }
}
