package io.jnyou;

import java.util.Arrays;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Sort
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Sort {


}

/**
 * 冒泡排序
 */
class BubbleSort {

    public static void main(String[] args) {
        /**
         * 50 2 33 90 57 1
         * 2 33 50 57 1 《90》 第一轮
         * 2 33 50 1 《57》  第二轮
         * 2 33 1 《50》  第三轮
         * 2 1 《33》  第四轮
         * 1 《2》  第五轮
         *
         * @param args
         */
        Integer[] nums = {50, 2, 33, 90, 57, 1};
        // 外循环控制轮数
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
//                    int temp = nums[j];
//                    nums[j] = nums[j+1];
//                    nums[j+1] = temp;
                    // nums[j] = nums[j+1]
                    nums[j] = nums[j] + nums[j + 1];
                    nums[j + 1] = nums[j] - nums[j + 1];
                    nums[j] = nums[j] - nums[j + 1];
                }
            }
        }

        for (int n = 0; n < nums.length; n++) {
            System.out.println(nums[n]);
        }
    }
}

/**
 * 选择排序
 */
class SelectorSort {

    /**
     * 选择第一个数为最小值||最大值 依次比较记录下标，最后换位
     * 50 2 33 90 57 1
     * 1 50 33 90 57 2 第一轮 （j = 1）
     * 1 2 50 90 57 33 第二轮 （j = 2）
     * 1 2 33 90 57 50 第三轮 （j = 3）
     * 1 2 33 50 90 57 第四轮 （j = 4）
     * 1 2 33 50 57 90 第五轮 （j = 5,包含本身，所以j<nums.length;）
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer[] nums = {50, 2, 33, 90, 57, 1};
        for (int i = 0; i < nums.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[minIdx] > nums[j]) {
                    // 换下标
                    minIdx = j;
                }
            }
            // 判断需要交换 的数是否为自己
            if (minIdx != i) {
                nums[minIdx] = nums[minIdx] + nums[i];
                nums[i] = nums[minIdx] - nums[i];
                nums[minIdx] = nums[minIdx] - nums[i];
            }
        }
        for (int k = 0; k < nums.length; k++) {
            System.out.println(nums[k]);
        }
    }

}


/**
 * 插入排序
 */
class InsertSort{
    /**
     *
     * 从后往前进行比较，记住操作的数，如果操作的数小于前面的数，那就前面的数往后移动，最后在将记录的操作数替换到前面的数位置上
     * 50, 2, 33, 90, 57, 1
     * 50 50 33 90 57 1 记录操作数为2，第一轮，替换位置：2 50 33 90 57 1
     * 2 50 50 90 57 1 记录数为33 第二轮：替换位置：2 33 50 90 57 1
     * .....
     *
     */
    public static void main(String[] args) {

        Integer[] nums = {50, 2, 33, 90, 57, 1};

        for(int i = 1;i < nums.length; i++) {
            // 记录操作数
            int temp = nums[i];
            int j = 0;
            for(j = i - 1;j>=0;j--){
                if(nums[j] > temp){
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            // 因为上面剪掉了一个  所以是j+1
            if(nums[j+1] != temp){
                nums[j+1] = temp;
            }
        }

        // 打印输出
        System.out.println(Arrays.toString(nums));
    }
}