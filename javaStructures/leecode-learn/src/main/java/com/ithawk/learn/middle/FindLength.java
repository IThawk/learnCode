package com.ithawk.learn.middle;

public class FindLength {


    /**
     * <p>
     *     给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
     *
     *  
     *
     * 示例：
     *
     * 输入：
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * 输出：3
     * 解释：
     * 长度最长的公共子数组是 [3, 2, 1] 。
     *  
     *
     * 提示：
     *
     * 1 <= len(A), len(B) <= 1000
     * 0 <= A[i], B[i] < 100
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    /**
     * <p>
     *     方法一：动态规划
     * 思路及算法
     *
     * 暴力解法的过程中，我们发现最坏情况下对于任意 i 与 j ，A[i] 与 B[j] 比较了 \min(i + 1, j + 1)min(i+1,j+1) 次。这也是导致了该暴力解法时间复杂度过高的根本原因。
     *
     * 不妨设 A 数组为 [1, 2, 3]，B 两数组为为 [1, 2, 4] ，那么在暴力解法中 A[2] 与 B[2] 被比较了三次。这三次比较分别是我们计算 A[0:] 与 B[0:] 最长公共前缀、 A[1:] 与 B[1:] 最长公共前缀以及 A[2:] 与 B[2:] 最长公共前缀时产生的。
     *
     * 我们希望优化这一过程，使得任意一对 A[i] 和 B[j] 都只被比较一次。这样我们自然而然想到利用这一次的比较结果。如果 A[i] == B[j]，那么我们知道 A[i:] 与 B[j:] 的最长公共前缀为 A[i + 1:] 与 B[j + 1:] 的最长公共前缀的长度加一，否则我们知道 A[i:] 与 B[j:] 的最长公共前缀为零。
     *
     * 这样我们就可以提出动态规划的解法：令 dp[i][j] 表示 A[i:] 和 B[j:] 的最长公共前缀，那么答案即为所有 dp[i][j] 中的最大值。如果 A[i] == B[j]，那么 dp[i][j] = dp[i + 1][j + 1] + 1，否则 dp[i][j] = 0。
     *
     * 这里借用了 Python 表示数组的方法，A[i:] 表示数组 A 中索引 i 到数组末尾的范围对应的子数组。
     *
     * 考虑到这里 dp[i][j] 的值从 dp[i + 1][j + 1] 转移得到，所以我们需要倒过来，首先计算 dp[len(A) - 1][len(B) - 1]，最后计算 dp[0][0]。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/zui-chang-zhong-fu-zi-shu-zu-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * </p>
     */

    class Solution {
        public int findLength(int[] A, int[] B) {
            int n = A.length, m = B.length;
            int[][] dp = new int[n + 1][m + 1];
            int ans = 0;
            for (int i = n - 1; i >= 0; i--) {
                for (int j = m - 1; j >= 0; j--) {
                    dp[i][j] = A[i] == B[j] ? dp[i + 1][j + 1] + 1 : 0;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
            return ans;
        }
    }
//
//    方法二：滑动窗口
//        思路及算法
//
//    我们注意到之所以两位置会比较多次，是因为重复子数组在两个数组中的位置可能不同。以 A = [3, 6, 1, 2, 4], B = [7, 1, 2, 9] 为例，它们的最长重复子数组是 [1, 2]，在 A 与 B 中的开始位置不同。
//
//    但如果我们知道了开始位置，我们就可以根据它们将 A 和 B 进行「对齐」，即：
//
//
//    A = [3, 6, 1, 2, 4]
//    B =    [7, 1, 2, 9]
//        ↑ ↑
//    此时，最长重复子数组在 A 和 B 中的开始位置相同，我们就可以对这两个数组进行一次遍历，得到子数组的长度，伪代码如下：
//
//
//    ans = 0
//    len = min(A.length, B.length)
//    k = 0
//        for i in [0 .. len - 1] do
//        if (A[i] == B[i]) then
//        k = k + 1
//    else
//    k = 0
//    end if
//    ans = max(ans, k)
//    end for
//    注意这里指定了 A[i] 对齐 B[i]，在实际代码实现中会通过指定初始位置 addA 与 addB 的方式来对齐，因此表达式会略有差别。
//
//    我们可以枚举 A 和 B 所有的对齐方式。对齐的方式有两类：第一类为 A 不变，B 的首元素与 A 中的某个元素对齐；第二类为 B 不变，A 的首元素与 B 中的某个元素对齐。对于每一种对齐方式，我们计算它们相对位置相同的重复子数组即可。



    class Solution1 {
        public int findLength(int[] A, int[] B) {
            int n = A.length, m = B.length;
            int ret = 0;
            for (int i = 0; i < n; i++) {
                int len = Math.min(m, n - i);
                int maxlen = maxLength(A, B, i, 0, len);
                ret = Math.max(ret, maxlen);
            }
            for (int i = 0; i < m; i++) {
                int len = Math.min(n, m - i);
                int maxlen = maxLength(A, B, 0, i, len);
                ret = Math.max(ret, maxlen);
            }
            return ret;
        }

        public int maxLength(int[] A, int[] B, int addA, int addB, int len) {
            int ret = 0, k = 0;
            for (int i = 0; i < len; i++) {
                if (A[addA + i] == B[addB + i]) {
                    k++;
                } else {
                    k = 0;
                }
                ret = Math.max(ret, k);
            }
            return ret;
        }
    }
//    复杂度分析
//
//    时间复杂度： O((N + M) \times \min(N, M))O((N+M)×min(N,M))。
//
//    空间复杂度： O(1)O(1)。
//
//    N 表示数组 A 的长度，M 表示数组 B 的长度。
//
//    作者：LeetCode-Solution
//    链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/zui-chang-zhong-fu-zi-shu-zu-by-leetcode-solution/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

}
