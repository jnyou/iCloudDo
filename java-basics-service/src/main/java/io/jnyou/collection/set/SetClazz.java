package io.jnyou.collection.set;

import java.util.*;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LinkedHashSetClazz
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class SetClazz {

    public static void main(String[] args) {
//        hashSet();
//        linkedHashSet();
        treeSet();
    }

    public static void hashSet() {
        // HashSet不保证插入的顺序，遍历输出效率较高，对象中所有属性一致则会去重
        // 底层是HashMap实现的
        Set<Enterprise> hashSet = new HashSet<>();
        hashSet.add(new Enterprise(2L,"上海科技有限公司",20L,15L));
        hashSet.add(new Enterprise(1L,"北京科技有限公司",20L,15L));
        hashSet.add(new Enterprise(3L,"天津科技有限公司",20L,15L));
        hashSet.add(new Enterprise(3L,"天津科技有限公司",20L,20L));
        hashSet.iterator().forEachRemaining(item -> System.out.println(item));
//        System.out.println(hashSet);
    }

    public static void linkedHashSet() {
        // LinkedHashSet保证了插入数据的顺序，对象中所有属性一致则会去重
        // 底层实现是LinkedHashMap,父类是HashSet
        Set<Enterprise> linkedSet = new LinkedHashSet<>();
        linkedSet.add(new Enterprise(2L,"上海科技有限公司",20L,15L));
        linkedSet.add(new Enterprise(1L,"北京科技有限公司",20L,15L));
        linkedSet.add(new Enterprise(3L,"天津科技有限公司",20L,15L));
        linkedSet.add(new Enterprise(3L,"天津科技有限公司",20L,20L));
        System.out.println(linkedSet);
    }

    public static void treeSet() {
        // 对于对象类型比较的话，没有给定比较规则则报错：cannot be cast to java.lang.Comparable
        // 两种方式：1、给对象实现Comparable接口，并且重写comparTo()方法进行比较，参数是比较的对象，this是当前对象。
        // 2、直接在创建TreeSet对象给一个Comparator比较器进行比较、
        // 主要作用于排序
        // 底层实现是TreeMap
        Set<Enterprise> treeSet = new TreeSet<>((o1, o2) -> {
            return (int) (o2.getParentId() - o1.getParentId());
        });
        treeSet.add(new Enterprise(2L,"上海科技有限公司",20L,15L));
        treeSet.add(new Enterprise(1L,"北京科技有限公司",20L,15L));
        treeSet.add(new Enterprise(3L,"天津科技有限公司",20L,15L));
        treeSet.add(new Enterprise(3L,"天津科技有限公司",20L,20L));

        System.out.println(treeSet);

    }


}