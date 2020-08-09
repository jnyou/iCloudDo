package org.jnyou.component.abstractbase;

/**
 * 分类名称
 *
 * @ClassName DivOperate
 * @Description: 相除的具体实现
 * @Author: jnyou
 * @create 2020/07/28
 * @module 智慧园区
 **/
public class DivOperate extends AbstractOperate {
    @Override
    public int compute(int numberA, int numberB) {
        return numberA / numberB;
    }
}