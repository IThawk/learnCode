package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2021/11/320:16
 */
public class SortList {

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
        public ListNode sortList(ListNode head) {

            if (head == null) {
                return null;
            }
            List<Integer> list = new ArrayList<>();
            while (head != null) {
                if (list.size() == 0) {
                    list.add(head.val);

                } else if (list.size() == 1) {
                    if (list.get(0) > head.val) {
                        list.add(list.get(0));
                        list.set(0, head.val);
                    } else {
                        list.add(head.val);
                    }
                } else {
                    int min = 0;
                    int max = list.size() - 1;

                    while (min <= max-1) {
                        int middle = (min + max) / 2;
                        if (list.get(min) >= head.val) {
                            list.add(list.get(list.size() - 1));
                            for (int i = list.size() - 1; i > 0; i--) {
                                list.set(i, list.get(i - 1));
                            }
                            list.set(0,head.val);
                            break;
                        } else if (list.get(max) <= head.val) {
                            list.add(head.val);
                            break;
                        } else {
                            if (list.get(middle) > head.val) {
                                if(middle-1==min){
                                    list.add(list.get(list.size()-1));
                                    for (int i = list.size() - 1; i > middle; i--) {
                                        list.set(i, list.get(i - 1));
                                    }
                                    list.set(middle,head.val);
                                    break;
                                }
                                max = middle;
                            } else if (list.get(middle) < head.val) {
                                if(middle+1==max){
                                    list.add(list.get(list.size()-1));
                                    for (int i = list.size() - 1; i > middle+1; i--) {
                                        list.set(i, list.get(i - 1));
                                    }
                                    list.set(middle+1,head.val);
                                    break;
                                }
                                min = middle;
                            } else {
                                list.add(list.get(list.size() - 1));
                                for (int i = list.size() - 1; i > middle; i--) {
                                    list.set(i, list.get(i - 1));
                                }
                                list.set(middle,head.val);
                                break;
                            }
                        }
                    }
                }
                head = head.next;
            }
            ListNode r = new ListNode(list.get(0));
            ListNode tmp = r;
            for (int i = 1; i < list.size(); i++) {
                tmp.next = new ListNode(list.get(i));
                tmp = tmp.next;
            }
            return r;
        }
    }

    //[4,19,14,5,-3,1,8,5,11,15]
    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode next3 = new ListNode(2);
       ListNode next2 = new ListNode(4, next3);
       ListNode next1 = new ListNode(-3, next2);
       ListNode next = new ListNode(-1, next1);
        ListNode head = new ListNode(1, next);
        solution.sortList(head);
        System.out.println("..........");
    }
}
