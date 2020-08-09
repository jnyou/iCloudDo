package org.jnyou.component.abstractbase;

/**
 * 分类名称
 *
 * @ClassName SubOperate
 * @Description: 相减的具体实现
 * @Author: jnyou
 * @create 2020/07/28
 * @module 智慧园区
 **/
public class SubOperate extends AbstractOperate {
    @Override
    public int compute(int numberA, int numberB) {
        return numberA - numberB;
    }
}