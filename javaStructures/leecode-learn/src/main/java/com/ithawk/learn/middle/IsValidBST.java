package com.ithawk.learn.middle;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/12/3020:38
 */
public class IsValidBST {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
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

    class Solution {
        public boolean isValidBST(TreeNode root) {
            if (root == null) return true;
            return validator(root.left, null, root.val)
                    && validator(root.right, root.val, null);
        }

        //* *定义一个辅助校验器
        public boolean validator(TreeNode root, Integer lowerBound, Integer upperBound) {
            if (root == null) return true;
            // 1.* *如果超出了下界，返回**false
            if (lowerBound != null && root.val <= lowerBound) {
                return false;
            }
            // 2.* *如果超出了上界，返回**false
            if (upperBound != null && root.val >= upperBound) {
                return false;
            }
            //* *接下来递归判断左右子树，返回结果
            return validator(root.left, lowerBound, root.val)
                    && validator(root.right, root.val, upperBound);
        }
    }
}
