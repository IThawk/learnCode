package com.ithawk.learn.difficult;

public class MergeKLists {


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {

            ListNode r = null;
            for (int i = 0; i < lists.length; i++) {
                r = m(r, lists[i]);
            }

            return r;
        }

        public ListNode m(ListNode l1, ListNode l2) {
            ListNode r = new ListNode(0);
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            ListNode p = r;
            while (l1 != null || l2 != null) {
                if (l1 != null) {
                    if (l2 == null) {
                        p.next = new ListNode(l1.val);
                        l1 = l1.next;
                        p = p.next;
                    } else {
                        if (l1.val <= l2.val) {
                            p.next = new ListNode(l1.val);
                            l1 = l1.next;
                            p = p.next;
                        } else {
                            p.next = new ListNode(l2.val);
                            l2 = l2.next;
                            p = p.next;
                        }
                    }
                } else {
                    p.next = new ListNode(l2.val);
                    l2 = l2.next;
                    p = p.next;
                }
            }
            return r.next;
        }
    }
}
