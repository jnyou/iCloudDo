package io.jnyou;

import java.util.Arrays;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * BinarySearch
 * 二分法查找算法
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class BinarySearch {

    /**
     * 前提  必须排序好了的数组
     * 定义一个数组最小的下标和最大的下标，相加除以2，判断这个数在左边还是右边，再继续查找
     *
     */
    public static void main(String[] args) {

        Integer[] nums = {50, 2, 33, 90, 57, 1};
        Arrays.sort(nums);
        System.out.println(searchMethod(nums,2));
    }

    public static int searchMethod(Integer nums[],int key) {
        int low = 0;
        int high = nums.length-1;
        while (low <= high)  {
            int middle = (low + high) >>> 1;
            if(nums[middle] > key){
                high = middle - 1;
            } else if(nums[middle] < key){
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

}