package com.ithawk.learn.leetCode.array;

import java.util.ArrayList;
import java.util.List;

public class ReverseList {

    public static void main(String[] args) {
        ListNode r = new ListNode(1);
        ListNode temp= r;
        r.next = new ListNode(2);
        r = r.next;
        r.next = new ListNode(3);
        r = r.next;
        r.next = new ListNode(4);
        r = r.next;
        r.next = new ListNode(5);
        r = r.next;
        ListNode s = new Solution().reverseList1(temp);
        System.out.println("out");
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static class Solution {
        //TODO
        public ListNode reverseList(ListNode head) {

            List<Integer> list= new ArrayList<>();
            while (head!=null){
                list.add(head.val);
                head = head.next;
            }
            if (list.isEmpty())
                return null;
            ListNode re = new ListNode(list.get(list.size()-1));
            ListNode re1 = re;
            for (int i =list.size()-2 ;i>=0;i--) {
                re1.next = new ListNode(list.get(i));
                re1 = re1.next;

            }
            return re;
        }

        public ListNode reverseList1(ListNode head) {
            ListNode prev = null; //前指针节点
            ListNode curr = head; //当前指针节点
            //每次循环，都将当前节点指向它前面的节点，然后当前节点和前节点后移
            while (curr != null) {
                ListNode nextTemp = curr.next; //临时节点，暂存当前节点的下一节点，用于后移
                curr.next = prev; //将当前节点指向它前面的节点
                prev = curr; //前指针后移
                curr = nextTemp; //当前指针后移
            }
            return prev;
        }
    }
}
