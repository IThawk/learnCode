package com.ithawk.learn.difficult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/719:31
 */
public class SolveNQueens {

    class Solution {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> r = new ArrayList<>();
            //用于存 放在 i 行 值为列
            int[] result = new int[n];
            setQueens(r,result,0,n);
            return r;

        }

        public void setQueens(List<List<String>> results, int[] result, int row, int n) {
            if (row == n) {
                print(result, results);
                return;
            }

            //从下方一直往上执行
            for (int col = 0; col < n; col++) {
                if (isOk(row, col,result,n)) {
                    result[row] = col;
                    setQueens(results, result, row + 1, n);
                }
            }

        }

        public void print(int[] result, List<List<String>> results) {

            for (int i = 0; i < result.length; i++) {
                List<String> list = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < result.length; j++) {
                    if (result[i] == j) {
                        stringBuilder.append("Q");
                    } else {
                        stringBuilder.append(".");
                    }
                }
                list.add(stringBuilder.toString());
                results.add(list);
            }
        }

        /**
         * @className Solution
         * @description:
         * @author IThawk
         * @date 2021/12/7 19:53
         */
        /**
         * @className Solution
         * @description:  row 行  col 列
         * @author IThawk
         * @date 2021/12/7 20:30
         */
        public boolean isOk(int row, int col, int[] result, int n) {

            //左对角线
            int leftup = col - 1;
            //右对角线
            int rightup = col + 1;


            for (int i = row - 1; i >= 0; i--) {

                //原列存在
                if (result[i] == col) {
                    return false;
                }

                //左对角线有
                if (leftup >= 0) {
                    if (result[i] == leftup) {
                        return false;
                    }
                }
                //右对角线存在
                if (rightup < n) {
                    if (result[i] == rightup) {
                        return false;
                    }
                }
                leftup--;
                rightup++;
            }
            return true;
        }
    }
}
