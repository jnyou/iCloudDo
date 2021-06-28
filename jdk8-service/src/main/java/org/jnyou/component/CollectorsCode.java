package org.jnyou.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jnyou.entity.Student;
import org.jnyou.util.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingInt;

/**
 * 分类名称
 *
 * @ClassName CollectorsCode
 * @Description: jdk8 stream operate jdk8处理流操作
 * @Author: jnyou
 **/
public class CollectorsCode {

    /**
     * 分组、过滤、求和、最值、排序、去重
     */
    public static void main(String[] args) {

        /**
         * 归约(reduce)
         */
        // 求和
        sumData();
        reduceAll1();
        reduceAll2();

        /**
         * 聚合（max/min/count)
         */
        bestValue();
        bestValue1();
        bestValue2();
        bestValue3();

        /**
         * 映射(map/flatMap)
         */
        //map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        //flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
        flatMap();

        /**
         * 统计(count/averaging)
         */
        // Collectors提供了一系列用于数据统计的静态方法：
        // 计数：count
        //  平均值：averagingInt、averagingLong、averagingDouble
        // 最值：maxBy、minBy
        // 求和：summingInt、summingLong、summingDouble
        // 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
        statistics();

        // List -> Map
        listToMap();

        /**
         * 排序(sorted)
         */
        sorted();
        sorted1();

        /**
         * 接合(joining)
         */
        joinin();

        /**
         * 提取/组合
         */
        // 去重
        distinct();
//        distinct1();

        // TreeSet单个属性去重
        clearSameSinglton();
        // TreeSet多个属性进行去重
        clearSameMoreArgs();

        /**
         * 分组(partitioningBy/groupingBy)
         */
        group();

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

    private static void distinct1() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        List<String> newLists = Stream.concat(stream1,stream2).collect(Collectors.toList());
        System.out.println(newLists+"合并");
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);
    }

    private static void joinin() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);
    }

    private static void group() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, "male", "New York"));
        personList.add(new Person("Jack", 7000, "male", "Washington"));
        personList.add(new Person("Lily", 7800, "female", "Washington"));
        personList.add(new Person("Anni", 8200, "female", "New York"));
        personList.add(new Person("Owen", 9500, "male", "New York"));
        personList.add(new Person("Alisa", 7900, "female", "New York"));

        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        // 将员工按性别分组
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);

        // 根据性别和地区同时分组
        System.out.println("==========================================================================");
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(value -> value.getSex() + "#" + value.getArea()));
        for (Map.Entry<String, List<Person>> stringListEntry : collect.entrySet()) {
            System.out.println(stringListEntry.getKey()); // female#New York
            System.out.println(stringListEntry.getValue()); // [CollectorsCode.Person(name=Anni, salary=8200, age=0, sex=female, area=New York), CollectorsTest.Person(name=Alisa, salary=7900, age=0, sex=female, area=New York)]
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Engine {
        Integer code;
        String value;
        Date moment;
    }

    private static void statistics() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        List<Engine> engines = new ArrayList<>();
        engines.add(new Engine(1115, "300", new Date()));
        engines.add(new Engine(1116, "300", new Date()));
        engines.add(new Engine(1117, "457", new Date()));

        // 求总数
        Long count = personList.stream().collect(counting());

        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));

        double v = engines.stream().map(item -> Double.parseDouble(item.getValue().trim())).mapToLong(Double::intValue).average().orElse(0D);
        // 保留两位小数
        BigDecimal b = new BigDecimal(v);
        double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));

        // 求工资之和
        Integer sum = personList.stream().collect(summingInt(Person::getSalary));

        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("String类型的平均值" + f);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }

    private static void flatMap() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
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

    public static void bestValue1() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");

        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    public static void bestValue2() {
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);

        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }

    public static void bestValue3() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max.get().getSalary());
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
        loadData().sort((a, b) -> a.getId().compareTo(b.getId()));
        loadData().stream().forEach(student -> System.out.println(student.getId()));
        System.out.println();
    }

    public static void sorted1() {
        List<Person> personList = new ArrayList<Person>();

        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
        personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 8800, 26, "male", "New York"));
        personList.add(new Person("Alisa", 9000, 26, "female", "New York"));

        // 按工资升序排序（自然排序）
        List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName)
                .collect(Collectors.toList());
        // 按工资倒序排序
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());
        // 先按工资再按年龄升序排序
        List<String> newList3 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());

        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);
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


    public static void reduceAll1() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);

    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;  // 姓名
        private int salary; // 薪资
        private int age; // 年龄
        private String sex; //性别
        private String area;  // 地区

        public Person(String name, int salary, String sex, String area) {
            this.name = name;
            this.salary = salary;
            this.sex = sex;
            this.area = area;
        }
    }

    public static void reduceAll2() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));

        // 求工资之和方式1：
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        // 求工资之和方式2：
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(),
                (sum1, sum2) -> sum1 + sum2);
        // 求工资之和方式3：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);
    }


    //初始化数据
    public static List<Student> loadData() {
        List<Student> list = Arrays.asList(
                new Student(1001L, "张三", "2班", "一年级", 85, 50, BigDecimal.ONE, new Date()),
                new Student(1002L, "李四", "2班", "一年级", 50, 70, BigDecimal.ONE, new Date()),
                new Student(1003L, "李四", "2班", "二年级", 55, 70, BigDecimal.ONE, new Date()),
                new Student(1004L, "张三", "1班", "一年级", 70, 70, BigDecimal.ONE, new Date()),
                new Student(1005L, "李四", "2班", "二年级", 60, 70, BigDecimal.ONE, new Date()),
                new Student(1006L, "张三", "2班", "一年级", 98, 70, BigDecimal.ONE, new Date())
        );
        return list;
    }

}

class newCollectors{
    //    toCollection
//    toList
//    toSet
//    toMap
//    joining
//    mapping/flatMapping
//    filtering
//    collectingAndThen
//    counting
//    minBy
//    maxBy
//    summingInt/summingLong/summingDouble
//    averagingInt/averagingLong/averagingDouble
//    groupingBy
//    groupingByConcurrent
//    partitioningBy
//    BinaryOperator
//    summarizingInt
    public static void toCollection() {
        List<String> strList = Arrays.asList("a", "b", "c", "b", "a");
        // toCollection()
        Collection<String> strCollection = strList.parallelStream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(strCollection); // [a, b, c]

        Set<String> strSet = strList.parallelStream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(strSet); // [a, b, c]

        List<String> strList1 = strList.parallelStream().sorted(String::compareToIgnoreCase)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(strList1); // [a, a, b, b, c]
    }

    public static void toList() {

        List<String> strList = Arrays.asList("a", "b", "c", "b", "a");

        List<String> uppercaseList = strList.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(uppercaseList); // [A, B, C, B, A]

        List<String> uppercaseUnmodifiableList = strList.parallelStream().map(String::toUpperCase)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        System.out.println(uppercaseUnmodifiableList); // [A, B, C, B, A]
    }

    public static void toSet() {

        List<String> strList = Arrays.asList("a", "b", "c", "b", "a");

        Set<String> uppercaseSet = strList.parallelStream().map(String::toUpperCase).collect(Collectors.toSet());
        System.out.println(uppercaseSet); // [A, B, C]

        Set<String> uppercaseUnmodifiableSet = strList.parallelStream().map(String::toUpperCase)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        System.out.println(uppercaseUnmodifiableSet); // [A, B, C]
    }

    // toMap
    public static void toMap() {
        Map<String, String> map = Stream.of("a", "b", "c")
                .collect(Collectors.toMap(Function.identity(), String::toUpperCase));
        System.out.println(map); // {a=A, b=B, c=C}

        // Duplicate Keys will throw: Exception in thread "main"
        // java.lang.IllegalStateException: Duplicate key a (attempted merging values A and A)
        Map<String, String> mapD = Stream.of("a", "b", "c", "b", "a")
                .collect(Collectors.toMap(Function.identity(), String::toUpperCase, String::concat));
        System.out.println(mapD); // {a=AA, b=BB, c=C}

        // above are HashMap, use below to create different types of Map
        TreeMap<String, String> mapTree = Stream.of("a", "b", "c", "b")
                .collect(Collectors.toMap(Function.identity(), String::toUpperCase, String::concat, TreeMap::new));
        System.out.println(mapTree); // {a=A, b=BB, c=C}
    }

    // 连接字符串
    public static void joining() {

        String concat = Stream.of("a", "b").collect(Collectors.joining());
        System.out.println(concat); // ab

        String csv = Stream.of("a", "b").collect(Collectors.joining(","));
        System.out.println(csv); // a,b

        String csv1 = Stream.of("a", "b").collect(Collectors.joining(",", "[", "]"));
        System.out.println(csv1); // [a,b]

        String csv2 = Stream.of("a", new StringBuilder("b"), new StringBuffer("c")).collect(Collectors.joining(","));
        System.out.println(csv2); // a,b

    }

    // mapping/flatMapping
    public static void flat_mapping() {

        Set<String> setStr = Stream.of("a", "a", "b")
                .collect(Collectors.mapping(String::toUpperCase, Collectors.toSet()));
        System.out.println(setStr); // [A, B]

        Set<String> setStr1 = Stream.of("a", "a", "b").flatMap(s -> Stream.of(s.toUpperCase()))
                .collect(Collectors.toSet());
        System.out.println(setStr1); // [A, B]
    }

    // 设置过滤条件
    public static void filtering() {
        List<String> strList2 = new ArrayList<>(Arrays.asList("1", "2", "10", "100", "20", "999"));
        Set<String> set = strList2.parallelStream().filter(s -> s.length() < 2)
                .collect(Collectors.toSet());
        System.out.println(set); // [1, 2]

    }

    // 返回一个收集器，该收集器将输入元素累积到给定的收集器中，然后执行其他完成功能
    public static void collectingAndThen() {
        List<String> strList2 = new ArrayList<>(Arrays.asList("1", "2", "10", "100", "20", "999"));

        List<String> unmodifiableList = strList2.parallelStream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        System.out.println(unmodifiableList); // [1, 2, 10, 100, 20, 999]
    }

    // 计数
    public static void counting() {

        Long evenCount = Stream.of(1, 2, 3, 4, 5).filter(x -> x % 2 == 0).collect(Collectors.counting());
        System.out.println(evenCount); // 2

    }

    // 根据给定的比较器返回最小元素
    public static void minBy() {
        Optional<Integer> min = Stream.of(1, 2, 3, 4, 5).collect(Collectors.minBy((x, y) -> x - y));
        System.out.println(min); // Optional[1]
    }

    // 根据给定的比较器返回最大元素
    public static void maxBy() {
        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5).collect(Collectors.maxBy((x, y) -> x - y));
        System.out.println(max); // Optional[5]
    }

    // summingInt/summingLong/summingDouble 求总和
    public static void summing() {
        List<String> strList3 = Arrays.asList("1", "2", "3", "4", "5");
        Integer sum = strList3.parallelStream().collect(Collectors.summingInt(Integer::parseInt));
        System.out.println(sum); // 15

        Long sumL = Stream.of("12", "23").collect(Collectors.summingLong(Long::parseLong));
        System.out.println(sumL); // 35

        Double sumD = Stream.of("1e2", "2e3").collect(Collectors.summingDouble(Double::parseDouble));
        System.out.println(sumD); // 2100.0
    }


    // averagingInt/averagingLong/averagingDouble  求平均值
    public static void averaging() {
        List<String> strList4 = Arrays.asList("1", "2", "3", "4", "5");
        Double average = strList4.parallelStream().collect(Collectors.averagingInt(Integer::parseInt));
        System.out.println(average); // 3.0

        Double averageL = Stream.of("12", "23").collect(Collectors.averagingLong(Long::parseLong));
        System.out.println(averageL); // 17.5

        Double averageD = Stream.of("1e2", "2e3").collect(Collectors.averagingDouble(Double::parseDouble));
        System.out.println(averageD); // 1050.0
    }

    // 分组
    public static void groupingBy() {
        Map<Integer, List<Integer>> mapGroupBy = Stream.of(1, 2, 3, 4, 5, 4, 3).collect(Collectors.groupingBy(x -> x * 10));
        System.out.println(mapGroupBy); // {50=[5], 20=[2], 40=[4, 4], 10=[1], 30=[3, 3]}
    }

    // 分组，是并发和无序的
    public static void groupingByConcurrent() {
        Map<Integer, List<Integer>> mapGroupBy = Stream.of(1, 2, 3, 4, 5, 4, 3).collect(Collectors.groupingByConcurrent(x -> x * 10));
        System.out.println(mapGroupBy); // {50=[5], 20=[2], 40=[4, 4], 10=[1], 30=[3, 3]}
    }

    // 返回一个Collector，它根据Predicate对输入元素进行分区，并将它们组织成Map <Boolean，List <T>>。
    public static void partitioningBy() {
        Map<Boolean, List<Integer>> mapPartitionBy = Stream.of(1, 2, 3, 4, 5, 4, 3).collect(Collectors.partitioningBy(x -> x % 2 == 0));
        System.out.println(mapPartitionBy); // {false=[1, 3, 5, 3], true=[2, 4, 4]}
    }

    // 返回一个收集器，它在指定的BinaryOperator下执行其输入元素的减少。这主要用于多级缩减，例如使用groupingBy（）和partitioningBy（）方法指定下游收集器
    public static void BinaryOperator() {

        Map<Boolean, Optional<Integer>> reducing = Stream.of(1, 2, 3, 4, 5, 4, 3).collect(Collectors.partitioningBy(
                x -> x % 2 == 0, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Integer::intValue)))));
        System.out.println(reducing); // {false=Optional[5], true=Optional[4]}
    }

    // 返回统计数据：min, max, average, count, sum
    public static void summarizingInt() {
        IntSummaryStatistics summarizingInt = Stream.of("12", "23", "35")
                .collect(Collectors.summarizingInt(Integer::parseInt));
        System.out.println(summarizingInt);
        //IntSummaryStatistics{count=3, sum=70, min=12, average=23.333333, max=35}
    }
}
