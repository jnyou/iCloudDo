package org.jnyou.component;

import org.jnyou.entity.Goods;

import java.util.Date;
import java.util.Optional;

/**
 * 分类名称
 *
 * @ClassName OptionalDit
 * @Description: Optional 类
 * @Author: jnyou
 **/
public class OptionalDict {

    /**
     * Optional<T> 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，原来用 null 表示一个值不存在，
     * 现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。
     * <p>
     * 常用方法：
     * Optional.of(T t) : 创建一个 Optional 实例
     * <p>
     * Optional.empty() : 创建一个空的 Optional 实例
     * <p>
     * Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
     * <p>
     * isPresent() : 判断是否包含值
     * <p>
     * orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
     * <p>
     * orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
     * <p>
     * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
     * <p>
     * flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
     **/

    public static void main(String[] args) {

        /**
         * of(T t)
         * of接收的参数，可能外部传入，可能为空，但是之前的方式真正报空指针还不知道在哪里
         * 但是如果Optional.of则始终都会报在这里，方便快速确定空指针的位置
         */
//        Optional<Goods> op1=Optional.of(null);


        /**
         * empty()
         * 该种方式是构建一个null，在第一行不会报错，get时候才报错
         */
//        Optional<Goods> op2= Optional.empty();
//        System.out.println(op2.get());

        /**
         * ofNullable(T t)
         * 传递进来能构建就构建对象，否则就构建null
         * 报错也是在get的时候
         * 其实就是of和empty的综合
         */
//        Optional<Goods> op3=Optional.ofNullable(null);
        Optional<Goods> op3 = Optional.ofNullable(new Goods(1001, "拿铁", new Date(), new Date()));
        System.out.println(op3.get().getGoodName());


        /**
         * isPresent()
         * 代表有值才get，等价于非空判断
         *
         */
        Optional<Goods> op4 = Optional.empty();
        if (op4.isPresent()) {
            System.out.println(op4.get());
        }

        /**
         * orElse(T t)
         * 如果op有值则获取，否则调用orElse的构造并返回该对象
         */
        Optional<Goods> op5 = Optional.ofNullable(null);
        Goods goods = op5.orElse(new Goods(1002, "速溶咖啡", new Date(), new Date()));
        System.out.println(goods.getGoodName());


        /**
         * orElseGet(Supplier s)  Supplier：供给形接口：提供者
         * 这个功能类似，只是参数是函数式接口，则可以内部写很复杂的逻辑，甚至根据条件返回不同的结果
         *
         */
        Goods goods1 = op5.orElseGet(() -> new Goods());
        Goods goods2 = op5.orElseGet(Goods::new);


        /**
         * map(Function f) Function：函数形接口：
         * 判断
         */
        Optional<Goods> op = Optional.of(new Goods(1003, "摩卡", new Date(), new Date()));
        Optional<String> str = op.map(e -> e.getGoodName());
        //此时获取的就是name而不是全部对象属性
        System.out.println(str.get());


        //和map基本相同，只是返回的必须是Optional,说白了，进一步避免空指针异常
        Optional<String> str2 = op.flatMap(e -> Optional.of(e.getGoodName()));
        System.out.println(str2.get());


    }


}