package com.ithawk.learn.middle;

/** <p>
 *
 *给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个下标。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 * 示例 2：
 *
 * 输入：nums = [3,2,1,0,4]
 * 输出：false
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * </p>
 * @author ithawk
 * @projectName javaStructures
 * @description: TODO
 * @date 2022/1/520:15
 */
public class CanJump {

    class Solution {
        //* *贪心策略：维护当前能到的最远位置
        public boolean canJump(int[] nums) {
            int farthest = 0;
            //* *遍历数组，更新**farthest
            for (int i = 0; i < nums.length; i++ ){
                if (i <= farthest){
                    farthest = Math.max(farthest, i + nums[i]);
                    if (farthest >= nums.length - 1)
                        return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }
}
