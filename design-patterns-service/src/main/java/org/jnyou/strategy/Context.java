package org.jnyou.strategy;

/**
 * @ClassName Context
 * @Description: 上下文操作，用于最终的策略选择,相当于spring开发中的controller层，调用service的方法返回。
 * @Author: jnyou
 **/
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1,num2);
    }
}