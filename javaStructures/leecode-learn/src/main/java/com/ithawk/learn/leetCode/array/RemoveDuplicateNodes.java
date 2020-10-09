package com.ithawk.learn.leetCode.array;

import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicateNodes {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(3);
        ListNode listNode5 = new ListNode(2);
        ListNode listNode6 = new ListNode(1);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        ListNode s = new Solution().removeDuplicateNodes(listNode1);
        System.out.println(s.toString());
    }

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * <p>
     * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     * <p>
     * 示例1:
     * <p>
     * 输入：[1, 2, 3, 3, 2, 1]
     * 输出：[1, 2, 3]
     * 示例2:
     * <p>
     * 输入：[1, 1, 1, 1, 2]
     * 输出：[1, 2]
     * 提示：
     * <p>
     * 链表长度在[0, 20000]范围内。
     * 链表元素在[0, 20000]范围内。
     * 进阶：
     * <p>
     * 如果不得使用临时缓冲区，该怎么解决？
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicate-node-lcci
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    static class Solution {
        public ListNode removeDuplicateNodes(ListNode head) {

            if (head == null) {
                return null;
            }
            Set<Integer> nums = new HashSet<Integer>();
            ListNode result = new ListNode(head.val);
            ListNode add = result;
            nums.add(head.val);
            head = head.next;
            while (head != null) {
                if (!nums.contains(head.val)) {
                    add.next = new ListNode(head.val);
                    nums.add(head.val);
                    add = add.next;

                }
                head = head.next;
            }

            return result;
        }
    }
}
