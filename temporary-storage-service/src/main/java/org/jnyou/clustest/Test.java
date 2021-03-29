package org.jnyou.clustest;

import org.apache.commons.collections.iterators.ArrayListIterator;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *
 * @className org.jnyou.clustest.Test
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class Test {

    public static void main(String[] args) {
        String s = "yyyy-MM-dd HH:mm:ss";
        LocalDate now = LocalDate.now();
        System.out.println(now);

        List<String> list = new ArrayList<>();
//        list.add("123");
//        list.add("3456");
//        list.add("789");
//        ListIterator listIterator = new ArrayListIterator(list);
//
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()){
//            String next = iterator.next();
//            System.out.println(next);
//        }

        Collections.unmodifiableCollection(list); // 不能被修改的集合 否则抛出 Java.lang.UnsupportedOperationException

//        for (Integer integer : list1) {
//            if(list.contains(integer)){
//                System.out.println(integer);
//            }
//        }

//        List<Integer> collect = list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
//        System.out.println(collect);

    }

}