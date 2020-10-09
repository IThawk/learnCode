package com.ithawk.learn.leetCode.array;

import java.util.ArrayList;
import java.util.List;

public class ReverseList {

    public static void main(String[] args) {
        ListNode r = new ListNode(1);
        r.next = new ListNode(2);
        r = r.next;
        r.next = new ListNode(3);
        r = r.next;
        r.next = new ListNode(4);
        r = r.next;
        r.next = new ListNode(5);
        r = r.next;
        ListNode s = new Solution().reverseList(r);
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
    }
}
