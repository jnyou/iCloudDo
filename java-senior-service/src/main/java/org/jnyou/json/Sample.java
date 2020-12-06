package org.jnyou.json;


import org.apache.commons.io.FileUtils;
import org.jnyou.entity.Address;
import org.jnyou.entity.Goods;
import org.jnyou.entity.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * 分类名称
 *
 * @ClassName Sample
 * @Description: json操作
 * @Author: jnyou
 **/
public class Sample {

    public static void main(String[] args) throws IOException {
        data1();
        data2();
        data3();
        new Sample().data4();

        genJsonFile();

        stringToJSONArray();

        mapToJSONArray();

        jsonArrayToMap();

        jsonStringToList();

        jsonStringToArray();

        listToJSONArray();
    }

    // map 转 json
    public static void data1() {
        Map<String, String> map = new HashMap<>();
        map.put("11", "11");
        map.put("22", "22");
        map.put("33", "33");
        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject);
    }

    // javaBean 转 json
    public static void data2() {
        Goods goods = new Goods();
        goods.setGoodName("测试");
        JSONObject jsonObject = new JSONObject(goods);
        System.out.println(jsonObject);
    }

    // 字符串 转 json
    public static void data3() {
        String str = "{\"11\":\"11\",\"22\":\"22\"}";
        JSONObject jsonObject = new JSONObject(str);
        System.out.println(jsonObject);
    }

    // 文件 转 json （文件-》String-》JSON）
    public void data4() throws IOException {
        // 方式一、
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("per.json");
//        byte [] bs = new byte[10];
//        int len = -1;
//        StringBuffer sb = new StringBuffer();
//        while ((len = in.read(bs)) != -1){
//            String str = new String(bs);
//            sb.append(str);
//        }
//        String s = sb.toString();

        // 方式二、（使用commons-io工具包的FileUtils工具）
        String s = FileUtils.readFileToString(new File("D:\\MeDrivers\\ideaPro\\iCloudDo\\java-senior-service\\src\\main\\resources\\per.json"), "UTF-8");

        JSONObject jsonObject = new JSONObject(s);
        System.out.println(jsonObject);
    }

    // map -> 生产JSON文件
    public static void genJsonFile() throws IOException {
        //准备json数据 (map->json)
        Map<String, Person> map = new HashMap<>();
        Person p1 = new Person(23, "zs", new Address("xa", "bj"));
        Person p2 = new Person(24, "ls", new Address("xa1", "bj1"));
        Person p3 = new Person(25, "ww", new Address("xa2", "bj2"));
        map.put("zs", p1);
        map.put("ls", p2);
        map.put("ww", p3);
        // map -> JSON
        JSONObject jsonObject = new JSONObject(map);
        // 生产JSON文件
        Writer writer = new FileWriter("e:\\test\\test.json");
        jsonObject.write(writer);
        writer.close();
    }

    // String格式的json数组 -> json数组
    public static void stringToJSONArray() {
        String jsonArrayStr = "[{\"name\":\"zs\",\"age\":23},{\"classname\":\"lq\",\"classno\":1},{\"schoolname\":\"xj\",\"zone\":\"xj\"}]";
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        System.out.println(jsonArray);
    }

    // map->JSONArray
    public static void mapToJSONArray() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        JSONArray array = new JSONArray();
        array.put(map);

        System.out.println(array);
    }

    // JSONArray -> map
    public static void jsonArrayToMap() {
        //准备jsonarry数据
        String jArrayStr = "["
                + "{\"name\":\"zs\",\"age\":23},"
                + "{\"classname\":\"lq\",\"classno\":1},"
                + "{\"schoolname\":\"xa\",\"zone\":\"xj\"}"
                + "]	";

        Map<String, Object> map = new HashMap<>();

        // jsonString -> jsonArray
        JSONArray array = new JSONArray(jArrayStr);

        // jsonArray -> map
        for (int i = 0; i < array.length(); i++) {
            Object o = array.get(i);
            JSONObject json = (JSONObject) o;
            // 获取每个JSON的key/value  -> map
            Set<String> keys = json.keySet();
            keys.forEach(key -> {
                Object value = json.get(key);
                map.put(key, value);
            });
        }
        System.out.println(map);
    }

    // json字符串 -> JSONArray -> List
    public static void jsonStringToList() {
        String jsonArrayStr = "[{\"name\":\"zs\",\"age\":23},{\"classname\":\"lq\",\"classno\":1},{\"schoolname\":\"xa\",\"zone\":\"xj\"}]" ;
        // json字符串 -> JSONArray
        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(jsonArrayStr);

        // JSONArray -> List
        List<Object> list = com.alibaba.fastjson.JSONObject.parseArray(array.toJSONString(), Object.class);
        System.out.println(list);
    }

    // json字符串 -> JSONArray -> 数组
    public static void jsonStringToArray() {
        String jsonArrayStr = "[{\"name\":\"zs\",\"age\":23},{\"classname\":\"lq\",\"classno\":1},{\"schoolname\":\"xa\",\"zone\":\"xj\"}]" ;
        // json字符串 -> JSONArray
        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(jsonArrayStr);

        // JSONArray -> 数组
        Object[] objects = array.toArray();
        System.out.println(objects[0]);
    }

    // List -> JSONArray
    public static void listToJSONArray() {
        List<String> names = new ArrayList<>() ;
        names.add("zs") ;
        names.add("ls") ;
        names.add("ww") ;

        JSONArray array = new JSONArray(names);
        System.out.println(array);
    }




}