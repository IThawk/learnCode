package com.ithawk.learn.middle;

/**<p>
 *
 * 74. 搜索二维矩阵
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 *
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * https://leetcode-cn.com/problems/search-a-2d-matrix/
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/2320:10
 */
public class SearchMatrix {


    static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix[0].length <= 0) {
                return false;
            }
            int font = 0;
            int end = matrix[0].length - 1;
            int mid = (font + end) / 2;
            int t = -1;
            while (font <= end) {
                mid = (font + end) / 2;
                if (t < 0) {
                    int j = matrix.length - 1;
                    for (; j >= 0; j--) {
                        if (target == matrix[j][font]) {
                            return true;
                        }
                        if (target == matrix[j][end]) {
                            return true;
                        }
                        if (target > matrix[j][font] && target < matrix[j][end]) {
                            t = j;
                            break;
                        }
                    }
                    if (t < 0) {
                        return false;
                    }
                }
                if (target > matrix[t][mid]) {
                    font = mid + 1;
                } else if (target < matrix[t][mid]) {
                    end = mid - 1;
                } else {
                    return true;
                }


            }
            return false;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3}};
        System.out.println(solution.searchMatrix(matrix, 3));
        ;
    }
}
