package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.List;

public class InorderTraversal {


    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {


        /**
         * 给定一个二叉树，返回它的中序 遍历。
         *
         * 示例:
         *
         * 输入: [1,null,2,3]
         *    1
         *     \
         *      2
         *     /
         *    3
         *
         * 输出: [1,3,2]
         * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
         *
         * 来源：力扣（LeetCode）
         * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal
         * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
         * @param root
         * @return
         */
        public List<Integer> inorderTraversal(TreeNode root) {

            List<Integer> list = new ArrayList<>();

            insert(list, root);
            return list;
        }

        public void insert(List<Integer> list, TreeNode root) {
            if (root == null) {
                return;
            }
            if (root.left != null) {
                TreeNode left = root.left;

                insert(list,left);
            }
            //中序遍历
            list.add(root.val);
            if (root.right != null) {

                TreeNode right = root.right;
                insert(list,right);
            }


        }
    }
}
