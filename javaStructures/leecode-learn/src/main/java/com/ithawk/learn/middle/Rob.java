package com.ithawk.learn.middle;

/**<p>
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 示例 2：
 *
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/1/620:43
 */
public class Rob {

    class Solution {
        public int rob(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            if (nums.length == 1) {
                return nums[0];
            }
            if (nums.length == 2) {
                return Math.max(nums[1], nums[0]);
            }
            int[] robs = new int[nums.length];
            robs[0] = nums[0];
            robs[1] = Math.max(nums[1], nums[0]);
            for (int i = 2; i < nums.length; i++) {
                robs[i] = Math.max(robs[i - 1], robs[i - 2] + nums[i]);
            }
            return robs[nums.length - 1];
        }

        public int rob1(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            if (nums.length == 1) {
                return nums[0];
            }
            if (nums.length == 2) {
                return Math.max(nums[1], nums[0]);
            }
            int[] robs = new int[nums.length];
            robs[0] = nums[0];
            robs[1] = Math.max(nums[1], nums[0]);
            int pre = nums[0];
            int pre1 = Math.max(nums[1], nums[0]);
            int tr = nums[0];
            for (int i = 2; i < nums.length; i++) {
                tr = Math.max(pre1, pre + nums[i]);
                pre = pre1;
                pre1 = tr;
            }
            return tr;
        }
    }
}
