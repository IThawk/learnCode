package com.ithawk.learn.middle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallest {

    //TODO
    class Solution {
        public int kthSmallest(int[][] matrix, int k) {
            int rows = matrix.length, columns = matrix[0].length;
            int[] sorted = new int[rows * columns];
            int index = 0;
            for (int[] row : matrix) {
                for (int num : row) {
                    sorted[index++] = num;
                }
            }
            Arrays.sort(sorted);
            return sorted[k - 1];
        }
    }

//    方法二：归并排序
//        思路及算法
//
//    由题目给出的性质可知，这个矩阵的每一行均为一个有序数组。问题即转化为从这 nn 个有序数组中找第 kk 大的数，可以想到利用归并排序的做法，归并到第 kk 个数即可停止。
//
//    一般归并排序是两个数组归并，而本题是 nn 个数组归并，所以需要用小根堆维护，以优化时间复杂度。
//
//    具体如何归并，可以参考力扣 23. 合并K个排序链表。



    class Solution2 {
        public int kthSmallest(int[][] matrix, int k) {
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return a[0] - b[0];
                }
            });
            int n = matrix.length;
            for (int i = 0; i < n; i++) {
                pq.offer(new int[]{matrix[i][0], i, 0});
            }
            for (int i = 0; i < k - 1; i++) {
                int[] now = pq.poll();
                if (now[2] != n - 1) {
                    pq.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
                }
            }
            return pq.poll()[0];
        }
    }
//    复杂度分析
//
//    时间复杂度：O(k\log{n})O(klogn)，归并 kk 次，每次堆中插入和弹出的操作时间复杂度均为 \log{n}logn。
//
//    空间复杂度：O(n)O(n)，堆的大小始终为 nn。


//    方法三：二分查找
//        思路及算法
//
//    由题目给出的性质可知，这个矩阵内的元素是从左上到右下递增的（假设矩阵左上角为 matrix[0][0]matrix[0][0]）。以下图为例：
//
//
//
//    我们知道整个二维数组中 matrix[0][0]matrix[0][0] 为最小值，matrix[n - 1][n - 1]matrix[n−1][n−1] 为最大值，现在我们将其分别记作 ll 和 rr。
//
//    可以发现一个性质：任取一个数 midmid 满足 l\leq mid \leq rl≤mid≤r，那么矩阵中不大于 midmid 的数，肯定全部分布在矩阵的左上角。
//
//    例如下图，取 mid=8mid=8：
//
//
//
//    我们可以看到，矩阵中大于 midmid 的数就和不大于 midmid 的数分别形成了两个板块，沿着一条锯齿线将这个矩形分开。其中左上角板块的大小即为矩阵中不大于 midmid 的数的数量。
//
//    读者也可以自己取一些 midmid 值，通过画图以加深理解。
//
//    我们只要沿着这条锯齿线走一遍即可计算出这两个板块的大小，也自然就统计出了这个矩阵中不大于 midmid 的数的个数了。
//
//    走法演示如下，依然取 mid=8mid=8：
//
//
//
//    可以这样描述走法：
//
//    初始位置在 matrix[n - 1][0]matrix[n−1][0]（即左下角）；
//
//    设当前位置为 matrix[i][j]matrix[i][j]。若 matrix[i][j] \leq midmatrix[i][j]≤mid，则将当前所在列的不大于 midmid 的数的数量（即 i + 1i+1）累加到答案中，并向右移动，否则向上移动；
//
//    不断移动直到走出格子为止。
//
//    我们发现这样的走法时间复杂度为 O(n)O(n)，即我们可以线性计算对于任意一个 midmid，矩阵中有多少数不小于它。这满足了二分答案的性质。
//
//    不妨假设答案为 xx，那么可以知道 l\leq x\leq rl≤x≤r，这样就确定了二分答案的上下界。
//
//    每次对于「猜测」的答案 midmid，计算矩阵中有多少数不大于 midmid ：
//
//    如果数量不多于 kk，那么说明最终答案 xx 不小于 midmid；
//    如果数量少于 kk，那么说明最终答案 xx 大于 midmid。
//    这样我们就可以计算出最终的结果 xx 了。




    class Solution1 {
        public int kthSmallest(int[][] matrix, int k) {
            int n = matrix.length;
            int left = matrix[0][0];
            int right = matrix[n - 1][n - 1];
            while (left < right) {
                int mid = left + ((right - left) >> 1);
                if (check(matrix, mid, k, n)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }

        public boolean check(int[][] matrix, int mid, int k, int n) {
            int i = n - 1;
            int j = 0;
            int num = 0;
            while (i >= 0 && j < n) {
                if (matrix[i][j] <= mid) {
                    num += i + 1;
                    j++;
                } else {
                    i--;
                }
            }
            return num >= k;
        }
    }
//    复杂度分析
//
//    时间复杂度：O(n\log(r-l))O(nlog(r−l))，二分查找进行次数为 O(\log(r-l))O(log(r−l))，每次操作时间复杂度为 O(n)O(n)。
//
//    空间复杂度：O(1)O(1)。

}
