package org.jnyou.strategy;

/**
 * 策略接口，（抽象策略(Strategy)角色）
 *
 * 主要操作需要做的事情(相当于spring框架开发的service层)
 *
 * @author 夏小颜
 */
public interface Strategy {

    /**
     * 执行计算的方法
     * @param num1
     * @param num2
     * @return
     */
    public int doOperation(int num1, int num2);

}
