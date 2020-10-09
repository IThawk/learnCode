package com.ithawk.learn.simple;

public class MaxDepth {


    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(2);
        TreeNode treeNode1 = new TreeNode(3);
        TreeNode treeNode2 = new TreeNode(4);
        treeNode1.left= treeNode2;
        treeNode.left = treeNode1;
        System.out.println(new Solution().maxDepth(treeNode));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * <p>
     *     给定一个二叉树，找出其最大深度。
     *
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度 3 。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    static class Solution {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }

            return Math.max(maxDepth(root.left) + 1, maxDepth(root.right) + 1);

        }
    }
}
