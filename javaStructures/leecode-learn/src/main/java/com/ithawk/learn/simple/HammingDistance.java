package com.ithawk.learn.simple;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: <p>
 *     两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
 *
 * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：x = 1, y = 4
 * 输出：2
 * 解释：
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 *        ↑   ↑
 * 上面的箭头指出了对应二进制位不同的位置。
 * 示例 2：
 *
 * 输入：x = 3, y = 1
 * 输出：1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/hamming-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 * @date 2022/2/1015:06
 */
public class HammingDistance {
    class Solution {

        public int hammingDistance(int x, int y) {

            int a = x^y;
            int i =0;
            while (a>0){
                if ((a&1)==1){
                    i++;
                }
                a>>=1;
            }
            return i;
        }
    }

}
