package org.jnyou.json;


import org.apache.commons.io.FileUtils;
import org.jnyou.entity.Goods;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 分类名称
 *
 * @ClassName Sample1
 * @Description: json操作
 * @Author: jnyou
 **/
public class Sample1 {

    public static void main(String[] args) throws IOException {
        data1();
        data2();
        data3();
        new Sample1().data4();
    }

    // map 转 json
    public static void data1() {
        Map<String,String> map = new HashMap<>();
        map.put("11","11");
        map.put("22","22");
        map.put("33","33");
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
        String s = FileUtils.readFileToString(new File("D:\\MeDrivers\\ideaPro\\iCloudDo\\java-senior-service\\src\\main\\resources\\per.json"),"UTF-8");

        JSONObject jsonObject = new JSONObject(s);
        System.out.println(jsonObject);
    }


}