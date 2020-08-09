package org.jnyou.component.abstractbase;

/**
 * 分类名称
 *
 * @ClassName Operate
 * @Description: 抽象类提取操作方法
 * @Author: jnyou
 * @create 2020/07/28
 * @module 智慧园区
 **/
public abstract class AbstractOperate {

    /**
     * 计算的抽象方法
     * 使用多态+反射机制实现代码的耦合度降低
     * @param numberA
     * @param numberB
     * @return int
     * @Author jnyou
     * @Date 2020/7/28
     */

    public abstract int compute(int numberA, int numberB);

}