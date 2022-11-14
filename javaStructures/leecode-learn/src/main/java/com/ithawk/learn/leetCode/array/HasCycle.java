package com.ithawk.learn.leetCode.array;

public class HasCycle {

    /**
     * Definition for singly-linked list.
     */
      class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
             val = x;
             next = null;
         }
      }

     class Solution {
        public boolean hasCycle(ListNode head) {
            if (head==null){
                return false;
            }
            ListNode slow = head;
            ListNode fast = head.next;
            if (fast==null){
                return false;
            }
            while (fast!=null && fast.next!=null && slow!=null){
                if (fast==slow){
                    return true;
                }
                fast = fast.next.next;
                slow = slow.next;
            }
            return false;
        }
    }
}
