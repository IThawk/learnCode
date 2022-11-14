package com.ithawk.learn.simple;

import java.util.*;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/2820:19
 */
public class IsBalanced {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Solution {
        public boolean isBalanced(TreeNode root) {

            List<Integer> integerList = new ArrayList<>();
            invertTree1(root, integerList, 0);
            integerList.sort(new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {

                    return o1.hashCode()-o2.hashCode();
                }
            });

            return integerList.get(integerList.size() - 1) - integerList.get(0) <= 1;
        }

        public void invertTree1(TreeNode root, List<Integer> integerList, int i) {

            if (root == null) {
                integerList.add(i);
                return;
            }

            invertTree1(root.left, integerList, i + 1);
            invertTree1(root.right, integerList, i + 1);

            List<String> stringList= new LinkedList<>();
            stringList.add(1,"ddd");

        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode root1 = new TreeNode(9);
        TreeNode root11 = new TreeNode(20);
        TreeNode root21 = new TreeNode(15);
        TreeNode root22 = new TreeNode(7);
        TreeNode root32 = new TreeNode(7);
        root.left=root1;
        root.right=root11;
        root11.left=root21;
        root11.right=root22;
        root22.right=root32;
        Solution solution = new Solution();
        System.out.println(solution.isBalanced(root));
    }
}
