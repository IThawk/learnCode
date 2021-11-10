package com.ithawk.learn.middle;

import java.util.*;

/** <p>
 * https://leetcode-cn.com/problems/insertion-sort-list/
 *
 *
 *147. 对链表进行插入排序
 * 对链表进行插入排序。
 *
 *
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 *
 *
 *
 * 插入排序算法：
 *
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 *
 *
 * 示例 1：
 *
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * 示例 2：
 *
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 *
 *
 *
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/316:24
 */
public class InsertionSortList {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static class Solution {
        public ListNode insertionSortList(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode r = new ListNode(head.val);
            while (head.next != null) {
                head = head.next;
                ListNode temp = r;
                boolean f = true;
                ListNode listNode = new ListNode(head.val);
                if (head.val <= temp.val) {
                    listNode.next = temp;
                    r = listNode;
                    continue;
                }
                while (temp.next != null) {
                    if (head.val <= temp.next.val) {
                        f = false;
                        listNode.next = temp.next;
                        temp.next = listNode;
                        break;
                    }

                    temp = temp.next;

                }
                if (f) {
                    temp.next = listNode;
                }
            }
            return r;
        }

        public ListNode insertionSortList1(ListNode head) {


            List<Integer> h = new ArrayList<>();
            while (head!=null){
                h.add(head.val);
                head= head.next;
            }

            Collections.sort(h);
            ListNode r = new ListNode(h.get(0));
            ListNode temp = r;
            for (int i=1;i<h.size();i++ ){
                temp.next= new ListNode(h.get(i));
                temp = temp.next;
            }
                return r;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode next3 = new ListNode(2);
        ListNode next2 = new ListNode(4, next3);
        ListNode next1 = new ListNode(-3, next2);
        ListNode next = new ListNode(-1, next1);
        ListNode head = new ListNode(1, next);
        solution.insertionSortList1(head);
        System.out.println("..........");
    }
}
