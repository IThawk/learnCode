package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.List;

public class Combine {
    public static void main(String[] args) {
        List<List<Integer>> re = new Solution().combine(4, 4);
        System.out.println();
    }

    /**
     * <p>
     * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     * <p>
     * 示例:
     * <p>
     * 输入: n = 4, k = 2
     * 输出:
     * [
     * [2,4],
     * [3,4],
     * [2,3],
     * [1,2],
     * [1,3],
     * [1,4],
     * ]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/combinations
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    static class Solution {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> re = new ArrayList<>();
            if (k == n) {
                List<Integer> list = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    list.add(i);

                }
                re.add(list);
                return re;
            }

            for (int i = 1; i <= n; i++) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                re.add(list);
            }

            for (int i = 2; i <= k; i++) {
                List<List<Integer>> newList = new ArrayList<>();
                for (List<Integer> list : re) {
                    int last = list.get(list.size() - 1);
                    for (int j = last + 1; j <= n; j++) {
                        List<Integer> list1 = new ArrayList<>();
                        list1.addAll(list);
                        list1.add(j);
                        newList.add(list1);
                    }
                }
                re = newList;
                //newList.clear();
            }

            return re;
        }

        private List<List<Integer>> getLists(int n, int k, List<List<Integer>> re1, int q) {
            List<List<Integer>> newList = new ArrayList<>();
            for (List<Integer> list : re1) {
                if (list.size() > 0) {
                    int last = list.get(list.size() - 1);
                    for (int i = last + 1; i <= n; i++) {
                        List<Integer> list1 = new ArrayList<>();
                        list1.addAll(list);
                        list1.add(i);
                        newList.add(list1);
                    }
                }

            }
            if (q == 1) {
//                List<List<Integer>> re = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    newList.add(list);
                }
                return getLists(n, k, newList, q + 1);
            } else if (q == k) {
                return newList;
            } else {

                return getLists(n, k, newList, q + 1);
            }

        }
    }
}
