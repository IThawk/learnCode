package com.ithawk.learn.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     https://leetcode-cn.com/problems/sort-colors/
 *     给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 *
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * 示例 2：
 *
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：[0]
 * 示例 4：
 *
 * 输入：nums = [1]
 * 输出：[1]
 *  
 *
 * 提示：
 *
 * n == nums.length
 * 1 <= n <= 300
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-colors
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description:
 * @date 2021/10/2910:36
 */
public class SortColors {


    static class Solution {
        public void sortColors(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : nums) {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) + 1);
                } else {
                    map.put(i, 1);
                }
            }

            final int[] j = {0};
            map.forEach((k, v) -> {
                for (int i = 0; i < v; i++) {
                    nums[j[0]] = k;
                    j[0]++;
                }

            });
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums ={2,0,2,1,1,0};
        solution.sortColors(nums);
        System.out.println();
    }
}
