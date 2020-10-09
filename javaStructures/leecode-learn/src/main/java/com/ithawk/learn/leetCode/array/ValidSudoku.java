package com.ithawk.learn.leetCode.array;


import java.util.HashSet;
import java.util.Set;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-05-16 22:59
 */
public class ValidSudoku {
    public static void main(String[] args) {
        char[][] nums = {{'.','.','.','.','5','.','.','1','.'},{'.','4','.','3','.','.','.','.','.'},{'.','.','.','.','.','3','.','.','1'},{'8','.','.','.','.','.','.','2','.'},{'.','.','2','.','7','.','.','.','.'},{'.','1','5','.','.','.','.','.','.'},{'.','.','.','.','.','2','.','.','.'},{'.','2','.','9','.','.','.','.','.'},{'.','.','4','.','.','.','.','.','.'}};
//        Solution s = new Solution();
        boolean e = new Solution().isValidSudoku(nums);
        System.out.println("jkjkjkjkj");
    }

    /**
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * <p>
     * <p>
     * <p>
     * 示例:
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * </p>
     *
     * @author IThawk
     * @description: //TODO
     * @date 2020/5/16 23:16
     * @param: null
     * @return
     */
    public static class Solution {

        public boolean isValidSudoku(char[][] board) {
            for (int i = 0; i < board.length; i += 3) {
                for (int j = 0; j < board[i].length; j += 3) {
                    for (int k = 0; k < 3; k++) {
                        Set<Integer> set = new HashSet<>();
                        for (int q = 0; q < 3; q++) {
                            if (board[i + k][j + q] != '.') {
                                Integer a = Integer.valueOf(board[i + k][j + q]);
                                if (set.contains(a)) {
                                    return false;
                                }
                                set.add(a);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < board.length; i += 1) {
                Set<Integer> set = new HashSet<>();
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != '.') {
                        Integer a = Integer.valueOf(board[i][j]);
                        if (set.contains(a)) {
                            return false;
                        }
                        set.add(a);
                    }
                }
            }
            for (int j = 0; j < board.length; j += 1) {
                Set<Integer> set = new HashSet<>();
                for (int i = 0; i < board[j].length; i++) {
                    if (board[i][j] != '.') {
                        Integer a = Integer.valueOf(board[i][j]);
                        if (set.contains(a)) {
                            return false;
                        }
                        set.add(a);
                    }
                }
            }


            return true;
        }
    }
}
