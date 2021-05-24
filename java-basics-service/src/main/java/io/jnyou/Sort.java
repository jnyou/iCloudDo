package io.jnyou;

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

class SelectorSort {

    /**
     * 选择第一个数为最小值||最大值 依次比较记录下标，最后换位
     * 50 2 33 90 57 1
     * 1 50 33 90 57 2 第一轮
     * 1 2 50 90 57 33 第二轮
     * 1 2 33 90 57 50 第三轮
     * 1 2 33 50 90 57 第四轮
     * 1 2 33 50 57 90 第五轮
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer []nums = {50, 2, 33, 90, 57, 1};
        for(int i = 0; i< nums.length -1; i ++){
            int minIdx = i;
            for(int j = i +1;j<nums.length;j++){
                if(nums[minIdx] > nums[j]){
                    // 换下标
                    minIdx = j;
                }
            }
            // 判断需要交换 的数是否为自己
            if(minIdx != i){
                nums[minIdx] = nums[minIdx] + nums[i];
                nums[i] = nums[minIdx] - nums[i];
                nums[minIdx] = nums[minIdx] - nums[i];
            }
        }
        for(int k = 0; k < nums.length;k++){
            System.out.println(nums[k]);
        }
    }

}