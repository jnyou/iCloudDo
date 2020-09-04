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
     * Conceptually, a functional interface has exactly one abstract
     *   method.  Since {@linkplain java.lang.reflect.Method#isDefault()
     *   default methods} have an implementation, they are not abstract.  If
     *   an interface declares an abstract method overriding one of the
     *   public methods of {@code java.lang.Object}, that also does
     *   <em>not</em> count toward the interface's abstract method count
     *   since any implementation of the interface will have an
     *   implementation from {@code java.lang.Object} or elsewhere.
     *
     *   <p>Note that instances of functional interfaces can be created with
     *   lambda expressions, method references, or constructor references.
     *
     *   <p>If a type is annotated with this annotation type, compilers are
     *   required to generate an error message unless:
     */

    /**
     * log builder
     *
     * @return
     * @Author jnyou
     */
    String buildMessage();

}