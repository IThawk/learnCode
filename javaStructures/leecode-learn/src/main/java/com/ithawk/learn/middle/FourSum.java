package com.ithawk.learn.middle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
//    TODO

    /**
     * <p>
     *     给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，
     *     使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
     *
     * 注意：
     *
     * 答案中不可以包含重复的四元组。
     *
     * 示例：
     *
     * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
     *
     * 满足要求的四元组集合为：
     * [
     *   [-1,  0, 0, 1],
     *   [-2, -1, 1, 2],
     *   [-2,  0, 0, 2]
     * ]
     * 通过次
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/4sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * </p>
     */
    class Solution {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            Arrays.sort(nums);
            int len = nums.length;
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            int c;
            int d;
            for(int i = 0; i <= len - 4; i++){
                if(i > 0 && nums[i] == nums[i-1]){
                    continue;
                }
                for(int j = i + 1; j <= len - 3; j++){
                    if(j > i + 1 && nums[j] == nums[j-1]){
                        continue;
                    }
                    c = j + 1;
                    d = len - 1;
                    while(c < d){
                        if(c > j + 1 && nums[c] == nums[c-1]){
                            c++;
                            continue;
                        }
                        if(nums[i]+nums[j]+nums[c]+nums[d] > target){
                            d--;
                        }else if(nums[i]+nums[j]+nums[c]+nums[d] < target){
                            c++;
                        }else{
                            res.add(new ArrayList<Integer>(Arrays.asList(nums[i],nums[j],nums[c],nums[d])));
                            c++;
                            d--;
                        }
                    }
                }
            }


            return res;
        }
    }
}
