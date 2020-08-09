package org.jnyou.service.templete;

/**
 * @ClassName BaseServiceHandle
 * @Description:
 * @Author: jnyou
 **/
@FunctionalInterface // 定义函数式接口
public interface BaseServiceHandle<T> {

    /**
     * 获取数据接口
     * @param <T>
     * @return
     */
    public T loadData();

}