package com.ithawk.learn.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeTwoLists {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(3);
        ListNode l11 = new ListNode(4);
        l1.next = l11;
        ListNode l2 = new ListNode(3);

        ListNode l22 = new ListNode(5);
        l2.next = l22;
        ListNode l23 = new ListNode(6);
        l22.next = l23;
        ListNode l24 = new ListNode(8);
        l23.next = l24;
        ListNode r = new Solution().mergeTwoLists1(l1, l2);
        System.out.println(".........");
    }

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
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            List<Integer> list = new ArrayList<>();
            while (l1 != null) {
                list.add(l1.val);
                l1 = l1.next;
            }
            while (l2 != null) {
                list.add(l2.val);
                l2 = l2.next;
            }
            Object[] re = list.toArray();
            Arrays.sort(re);
            ListNode r = new ListNode();
            ListNode pre = r;
            for (int i = 0; i < re.length; i++) {
                pre.next = new ListNode((int) re[i]);
                pre = pre.next;
            }

            return r.next;
        }

        public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
            ListNode r = new ListNode();
            ListNode pre = r;
            while (l1 != null || l2 != null) {

                if (l1!=null){

                    if (l2!=null){
                        if (l1.val<=l2.val){
                            pre.next = new ListNode(l1.val);
                            pre = pre.next;
                            l1= l1.next;
                        }else{
                            pre.next = new ListNode(l2.val);
                            pre = pre.next;
                            l2= l2.next;
                        }
                    }else{
                        pre.next = new ListNode(l1.val);
                        pre = pre.next;
                        l1= l1.next;
                    }
                }else{
                    pre.next = new ListNode(l2.val);
                    pre = pre.next;
                    l2= l2.next;
                }


            }
            return r.next;
        }
    }
}
