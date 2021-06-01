package io.commchina;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TwoSum0001
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class TwoSum0001 {

//    题目
//    Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//
//    You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//     Example:
//
//    Given nums = [2, 7, 11, 15], target = 9,
//
//    Because nums[0] + nums[1] = 2 + 7 = 9,
//            return [0, 1].


//    题目大意
//    在数组中找到 2 个数之和等于给定值的数字，结果返回 2 个数字在数组中的下标。

    public static void main(String[] args) {
        Integer target = 9;
        Integer []nums = new Integer[]{2, 7, 11, 15,2};
        // 0 1 2
        // i = 0 j = 1 j = 2 j = 3
        // i = 1 j = 2 3
        // i = 2 j = 3

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if(target.equals(nums[i] + nums[j])){
                    System.out.print("第一个数的下标为" + i + "第二个数的下标为" + j);
                }
            }
        }
    }


}