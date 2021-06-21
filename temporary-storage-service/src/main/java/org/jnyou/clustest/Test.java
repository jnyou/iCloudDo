package org.jnyou.clustest;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.lang.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusMonths(-2);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LocalDate analysisDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        System.out.println(analysisDate);
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

        String[] name = {"青海分公司","安徽分公司",
                "陕西分公司","山西分公司","宁夏分公司","甘肃分公司","西藏分公司",
                "新疆分公司","四川分公司","云南分公司","湖北分公司","重庆分公司",
                "湖南分公司","江西分公司","中移在线服务有限公司","江苏分公司","天津分公司",
                "福建分公司","北京分公司","浙江分公司","河北分公司","广西分公司",
                "广东分公司","内蒙古分公司","上海分公司","辽宁分公司","黑龙江分公司","贵州分公司",
                "山东分公司","吉林分公司","海南分公司","河南分公司"};

        String[] name1 = {
                "广东分公司",
                "北京分公司",
                "天津分公司",
                "河北分公司",
                "安徽分公司",
                "辽宁分公司",
                "云南分公司",
                "湖南分公司",
                "浙江分公司",
                "河南分公司",
                "上海分公司",
                "福建分公司",
                "重庆分公司",
                "广西分公司",
                "湖北分公司",
                "黑龙江分公司",
                "吉林分公司",
                "四川分公司",
                "山东分公司"};
        List list1 = Arrays.asList(name1);
        for (String s1 : name) {
            if(!list1.contains(s1)) {
                System.out.println(s1);
            }
        }

        LinkedList list2 = new LinkedList();
        String sw = "咨询回访 , 业务办理 , 订单服务 , 语音通知";
        String s1 = "邀约推广 , 咨询回访 , 业务办理 , 语音通知";
        sw = sw.replaceAll(" +","");
        s1 = s1.replaceAll(" +","");
        String[] split = sw.split(",");
        String[] split1 = s1.split(",");

        List<String> all = new ArrayList<>();
        all.addAll(Arrays.asList(split));
        all.addAll(Arrays.asList(split1));
        List<String> collect = all.stream().distinct().collect(Collectors.toList());
        String strip = StringUtils.strip(collect.toString(), "[]");
        System.out.println(strip);
//        List<Integer> collect = list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
//        System.out.println(collect);

    }

}