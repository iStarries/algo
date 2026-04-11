import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

    }
}

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        /*
        先排序
        for循环确定一个值i，另外两个值变成两数之和（双指针）
        更新i的时候去重：不能和上一个i相等，不然只有可能找到相同三元组
        找到三元组后，对left,right去重，二者都不能和之前的值一样，不然只可能会找到相同三元组
         */
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            //no repeat i
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1, right = nums.length - 1;
            int target = 0 - nums[i];

            if (left < right) {
                while (left < right) {
                    if (nums[left] + nums[right] == target) {
                        res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        left++;
                        right--;
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    }
                    //no repeat left & right
                    else if (nums[left] + nums[right] > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            } else {
                continue;
            }
        }
        return res;
    }
}