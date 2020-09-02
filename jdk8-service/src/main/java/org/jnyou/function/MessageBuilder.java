package org.jnyou.function;

/**
 * 分类名称
 *
 * @ClassName MessageBuilder
 * @Description: 函数式接口构建
 * @Author: jnyou
 **/
@FunctionalInterface
public interface MessageBuilder {

    /**
     * log builder
     * @return
     * @Author jnyou
     */
    String buildMessage();

}