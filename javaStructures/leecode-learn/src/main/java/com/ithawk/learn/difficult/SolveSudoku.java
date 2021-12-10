package com.ithawk.learn.difficult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/720:38
 */
public class SolveSudoku {

    static class Solution {
        public void solveSudoku(char[][] board) {

            //存各行的char
            Map<Integer, Set<Integer>> rowMap = new HashMap<>();

            //存各行的char
            Map<Integer, Set<Integer>> colMap = new HashMap<>();

            //存各区域的char
            Map<Integer, Set<Integer>> xterMap = new HashMap<>();

            set(board, 0, 0, rowMap, colMap, xterMap);
            System.out.println("");
        }

        private void set(char[][] board, int row, int coli, Map<Integer, Set<Integer>> rowMap, Map<Integer, Set<Integer>> colMap, Map<Integer, Set<Integer>> xterMap) {

            if (coli == board.length - 1) {
                if (row == board.length - 1) {
                    return;
                }
                row++;
            }
            for (int col = 0; col < board.length; col++) {
                int xter = Integer.parseInt(row / 3 + "" + col / 3);
                if (rowMap.get(row) == null) {
                    rowMap.put(row, new HashSet<>());
                }
                if (colMap.get(col) == null) {
                    colMap.put(col, new HashSet<>());
                }
                if (xterMap.get(xter) == null) {
                    xterMap.put(xter, new HashSet<>());
                }
                if (board[row][col] != '.') {

                    rowMap.get(row).add(Integer.valueOf(board[row][col] + ""));
                    colMap.get(col).add(Integer.valueOf(board[row][col] + ""));
                    //区域的算法
                    xterMap.get(xter).add(Integer.valueOf(board[row][col] + ""));
                } else {
                    for (int j = 1; j <= 9; j++) {
                        if (isOk(row, col, xter, rowMap, colMap, xterMap, j)) {
                            board[row][col] = (char) ('0' + j);
                            rowMap.get(row).add(Integer.valueOf(board[row][col] + ""));
                            colMap.get(col).add(Integer.valueOf(board[row][col] + ""));
                            //区域的算法
                            xterMap.get(xter).add(Integer.valueOf(board[row][col] + ""));
                            set(board, row, coli + 1, rowMap, colMap, xterMap);
                        }
                    }

                }


            }
        }

        private boolean isOk(int row, int col, int xter, Map<Integer, Set<Integer>> rowMap, Map<Integer, Set<Integer>> colMap, Map<Integer, Set<Integer>> xterMap, int j) {

            if (rowMap.get(row).contains(j)) {
                return false;
            }
            if (colMap.get(col).contains(j)) {
                return false;
            }
            return !xterMap.get(xter).contains(j);
        }
    }

    public static void main(String[] args) {
        int i = 1;
        char c = (char) ('0' + i);
        ;
        System.out.println(c);
        System.out.println(Integer.valueOf(c + ""));
        char[][] b = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        Solution solution = new Solution();
        solution.solveSudoku(b);
        System.out.println(c);

    }
}
