package com.ithawk.learn.swordOffer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  

 示例 1：

 输入：[3,4,5,1,2]
 输出：1
 示例 2：

 输入：[2,2,2,0,1]
 输出：0

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Offer11 {
    public int minArray(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (min > numbers[i]) {
                return numbers[i];
            }
            if (numbers[i-1]>numbers[i]){
                break;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Offer11 offer10 = new Offer11();
        int[] i = {3,4,5,1,2};
        System.out.println(offer10.minArray(i));
        ;
    }
}