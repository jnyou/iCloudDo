package org.jnyou.strategy;

/**
 * @ClassName OperationSubtract
 * @Description: 具体实现（需要操作的具体方法,相当于spring开发impl实现service接口层）
 * @Author: jnyou
 **/
public class OperationSubtract implements Strategy {
    /**
     * 此实现用于相减的具体操作
     * @param num1
     * @param num2
     * @return
     */
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}