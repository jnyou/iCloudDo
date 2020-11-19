package org.jnyou.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.jnyou.entity.Student;
import org.jnyou.util.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分类名称
 *
 * @ClassName CollectorsTest
 * @Description: jdk8 stream operate jdk8处理流操作
 * @Author: jnyou
 **/
public class CollectorsTest {

    /**
     * 分组、过滤、求和、最值、排序、去重
     */
    public static void main(String[] args) {

        // 求和
        sumData();

        // 最值
        sumData();

        // List -> Map
        listToMap();

        // 排序
        sorted();

        // distinct去重
        distinct();

        // TreeSet单个属性去重
        clearSameSinglton();

        // TreeSet多个属性进行去重
        clearSameMoreArgs();

        // 分组：根据name进行分组 得到的是对象
        Map<String, List<Student>> resultListObj = loadData().stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(JSON.toJSONString(resultListObj, SerializerFeature.PrettyFormat));
        //遍历分组
        for (Map.Entry<String, List<Student>> entryUser : resultListObj.entrySet()) {
            String key = entryUser.getKey();
            List<Student> entryUserList = entryUser.getValue();
        }
        /**
         * {
         * 	"李四":[
         *        {
         * 			"className":"2班",
         * 			"grade":"一年级",
         * 			"name":"李四"
         *        },
         *        {
         * 			"className":"2班",
         * 			"grade":"二年级",
         * 			"name":"李四"
         *        },
         *        {
         * 			"className":"2班",
         * 			"grade":"二年级",
         * 			"name":"李四"
         *        }
         * 	],
         * 	"张三":[
         *        {
         * 			"className":"2班",
         * 			"grade":"一年级",
         * 			"name":"张三"
         *        },
         *        {
         * 			"className":"1班",
         * 			"grade":"一年级",
         * 			"name":"张三"
         *        },
         *        {
         * 			"className":"2班",
         * 			"grade":"一年级",
         * 			"name":"张三"
         *        }
         * 	]
         * }
         */

        // 分组：根据name分组，得到的对象中的属性
        Map<String, List<String>> resultList = loadData().stream().collect(Collectors.groupingBy(Student::getName, Collectors.mapping(Student::getName, Collectors.toList())));
        System.out.println(JSON.toJSONString(resultList, SerializerFeature.PrettyFormat));
        /**
         * {
         * 	"李四":[
         * 		"李四",
         * 		"李四",
         * 		"李四"
         * 	],
         * 	"张三":[
         * 		"张三",
         * 		"张三",
         * 		"张三"
         * 	]
         * }
         */
    }


    // 求和： 分基本类型和大数类型求和，基本类型先mapToInt，然后调用sum方法，大数类型使用reduce调用BigDecimal::add方法
    public static void sumData() {
        // 基本数据类型求和
        int sumAge = loadData().stream().mapToInt(Student::getSocre).sum();

        // BigDecimal求和
        BigDecimal totalQuantity = loadData().stream().map(Student::getFamilyMemberQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 上面的求和不能过滤bigDecimal对象为null的情况，可能会报空指针，这种情况，我们可以用filter方法过滤，或者重写求和方法
        BigDecimal totalQuantity2 = loadData().stream().map(Student::getFamilyMemberQuantity).reduce(BigDecimal.ZERO, BigDecimalUtils::sum);
    }


    // 最值 求最小与最大，使用min max方法
    public static void bestValue() {
        //最小
        Date minEntryDate = loadData().stream().map(Student::getEntryDate).min(Date::compareTo).get();

        //最大
        Date maxEntryDate = loadData().stream().map(Student::getEntryDate).max(Date::compareTo).get();
    }


    // List 转 map
    public static void listToMap() {
        /**
         * List -> Map
         * 需要注意的是：
         * toMap 如果集合对象有重复的key，会报错Duplicate key ....
         * user1,user2的id都为1。
         * 可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
         */
        Map<Long, Student> userMap = loadData().stream().collect(Collectors.toMap(Student::getId, a -> a, (k1, k2) -> k1));
    }


    // 排序  可通过Sort对单字段多字段排序
    public static void sorted() {
        //单字段排序，根据id排序  默认升序
        loadData().sort(Comparator.comparing(Student::getId));
        //多字段排序，根据id，年龄排序 默认升序
        loadData().sort(Comparator.comparing(Student::getId).thenComparing(Student::getSocre));

        // 使用stream和sort--降序排列-----------
        loadData().stream().sorted(Comparator.comparing(Student::getId).reversed()).collect(Collectors.toList());

        // 使用集合的sort排序，集合自身排序发生变化
        loadData().sort((a,b)->a.getId().compareTo(b.getId()));
        loadData().stream().forEach(student -> System.out.println(student.getId()));
        System.out.println();

    }


    // 去重  可通过distinct方法进行去重
    public static void distinct() {
        //去重
        List<Long> idList = new ArrayList<Long>();
        idList.add(1L);
        idList.add(1L);
        idList.add(2L);
        List<Long> distinctIdList = idList.stream().distinct().collect(Collectors.toList());
    }
    // TreeSet多条件去重
    public static void clearSameMoreArgs() {

        ArrayList<Student> collect1 = loadData().stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(o -> o.getName() + ";" + o.getClassName() + ";" + o.getGrade()))), ArrayList::new));
        System.out.println("多条件去重后:" + collect1.toString());
    }
    // TreeSet单条件去重
    public static void clearSameSinglton() {
        List<Student> collect = loadData().stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Student::getName))), ArrayList::new));
        System.out.println("name去重后:" + collect.toString());
    }





    //初始化数据
    public static List<Student> loadData() {
        List<Student> list = Arrays.asList(
                new Student(1001L,"张三", "2班", "一年级", 85, BigDecimal.ONE, new Date()),
                new Student(1002L,"李四", "2班", "一年级", 50, BigDecimal.ONE, new Date()),
                new Student(1003L,"李四", "2班", "二年级", 55, BigDecimal.ONE, new Date()),
                new Student(1004L,"张三", "1班", "一年级", 70, BigDecimal.ONE, new Date()),
                new Student(1005L,"李四", "2班", "二年级", 60, BigDecimal.ONE, new Date()),
                new Student(1006L,"张三", "2班", "一年级", 98, BigDecimal.ONE, new Date())
        );
        return list;
    }

}